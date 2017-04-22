/**   
 * filename：UserService.java
 *   
 * date：2016年4月12日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lol.mifan.myblog.dao.UserDAO;
import lol.mifan.myblog.model.Users;
import lol.mifan.myblog.resolver.HttpException;
import lol.mifan.myblog.service.UserService;
import lol.mifan.myblog.util.MyUtils;
import lol.mifan.myblog.util.encryption.PasswordService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;




@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDAO userDao;
	@Resource
	private PasswordService passwordService;
	@Resource
	private PasswordService tokenService;
	@Resource
	private Long tokenValidity;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Transactional
	@Override
	public Map<String, Object> add(Users user) throws HttpException {
		
		if(user.getUsername() == null || !user.getUsername().matches("^\\S{3,20}$")) {
			logger.info(user.getUsername() + "用户名不符合要求");
			throw new HttpException(400, "用户名不符合要求");
		}
		if(user.getUsername() == null || !user.getPassword().matches("^\\S{6,18}$")) {
			logger.info(user.getUsername() + "密码不符合要求");
			throw new HttpException(400, "密码不符合要求");
		}
//		if(user.getUsername() == null || user.getPassword().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
//			logger.info(user.getUsername() + "邮箱不符合要求");
//			throw new HttpException(400, "邮箱不符合要求");
//		}
//		if(user.getUsername() == null || user.getPassword().matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
//			logger.info(user.getUsername() + "邮箱不符合要求");
//			throw new HttpException(400, "邮箱不符合要求");
//		}
		
		if(userDao.findActiveByUsername(user.getUsername()) != null) {
			logger.info(user.getUsername() + "用户已存在");
			throw new HttpException(409, "用户已存在");
		}
		
		
		
		user.setId(null);
		user.setEmail(null);
		user.setPhone(null);
		user.setAvatar(null);
		user.setArticles(new HashSet<>(0));
		user.setCreateTime(new Date());
		user.setDeleted(false);
		user.setLastLoginTime(new Date(0));
		user.setNote("");
		String encryptPwd = encryptPwd(user.getPassword(), user.getUsername());
		user.setPassword(encryptPwd);
		user.setToken(tokenFromUser(user));
		
		userDao.save(user);
		
		return bindUser(user);
	}
	
	
	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> get(Serializable id) throws HttpException {
		Users user = userDao.getActive(id);
		
		if(user == null) {
			logger.info(id + "用户不存在");
			throw new HttpException(404, "用户不存在");
		}
		return bindUser(user);
	}



	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public List<Map<String, Object>> getList(Integer offset, Integer limit, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> token(String username, String password) throws HttpException {
		
		Users user = userDao.findActiveByUsername(username);
		
		if(user == null){//用户名不存在
			logger.info("用户" + username + "登录，用户名不存在");
			throw new HttpException(403, "用户名不存在");
		}
		
		String encryptPwd = encryptPwd(password, username);
		if(!encryptPwd.equals(user.getPassword())){//密码错误
			logger.info("用户" + username + "登录，密码错误");
			throw new HttpException(401, "密码错误");
		}
		
		String token = tokenFromUser(user);
        
        user.setToken(token);
        user.setLastLoginTime(new Date());
		userDao.update(user);
        
		
		logger.info("用户" + username + "登录，登录成功");
		
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		return map;
	}



	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public void loginByToken(String token) throws HttpException {
		
		Users user = userDao.findEntityByColumn("token", token);
		
		if(!MyUtils.isEmptyOrNull(token) && user != null){
			long interval = new Date().getTime() - user.getLastLoginTime().getTime();
			if(interval < tokenValidity*24*60*60*1000){//转换成天
				//登陆成功
				user.setLastLoginTime(new Date());
				userDao.update(user);
				logger.info("用户" + user.getUsername() + "登录，登录成功");
			}
		} else {
			//token无效
			logger.info("token：" + token + "无效");
			throw new HttpException(401, "token无效");
		}
	}




	@Transactional
	@Override
	public void logout(String token) {
//
////		Users user = userDao.findEntityByColumn("token", token);
////		user.setToken(null);
////		userDao.update(user);
////		
////		
////		jsonData.setStateCode(MyStatus.OK);
	}







	@Transactional
	@Override
	public void editUserpwd(String username, String oldUserpwd,
			String newUserpwd) {

		Users user = userDao.findActiveByUsername(username);
		
		String encryptPwd = passwordService.encrypt(oldUserpwd, username);

		if(encryptPwd.equals(user.getPassword())) {
			user.setPassword(passwordService.encrypt(newUserpwd, username));
			userDao.update(user);
		}
		
	}









	







	
	





	@Override
	public String encryptPwd(String password, String username) {
		return tokenService.encrypt(password, username);
	}




	@Override
	public String tokenFromUser(Users user) {
		return tokenService.encrypt(user.getUsername(), user.getPassword() + new Date().getTime());
	}





	private Map<String, Object> bindUser(Users user) {
		Map<String, Object> map = new HashMap<>();

		map.put("id", user.getId());
		map.put("username", user.getUsername());
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("avatar", user.getAvatar());
		return map;
	}

	


	







	



}
