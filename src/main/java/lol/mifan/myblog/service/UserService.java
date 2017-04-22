/**   
 * filename：BaseService.java
 *   
 * date：2016年4月19日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lol.mifan.myblog.model.Users;
import lol.mifan.myblog.resolver.HttpException;



public interface UserService {
	
	/**   
	 * 按ID取单个
	 * @author 范子才
	 * @param id
	 * @return
	 * @version 2017年3月12日 下午3:54:47
	 */
	Map<String, Object> get(Serializable id) throws HttpException;
	
	/**   
	 * 分页取List
	 * @author 范子才
	 * @param offset
	 * @param limit
	 * @param query
	 * @return
	 * @version 2017年3月22日12:08:26
	 */
	List<Map<String, Object>> getList(Integer offset, Integer limit, String query) throws HttpException;
	
	
	/**   
	 * 获取token
	 * @author 范子才
	 * @param username 用户名
	 * @param password 用户的未加密密码
	 * @param session
	 * @return 包含token的jsonData
	 * @version 2016年4月21日 下午3:58:46
	 */
	Map<String, Object> token(String username, String password) throws HttpException;
	
	/**   
	 * token登录
	 * @author 范子才
	 * @param token
	 * @param session
	 * @return 登陆JSON信息
	 * @version 2016年4月21日 下午3:58:46
	 */
	void loginByToken(String token) throws HttpException;
	
	



	/**   
	 * 注销登录
	 * @author 范子才
	 * @param token
	 * @return
	 * @version 2016年5月10日 上午10:59:58
	 */
	void logout(String token) throws HttpException;


	/**   
	 * 修改密码
	 * @author 范子才
	 * @param username
	 * @param oldUserpwd
	 * @param newUserpwd
	 * @return
	 * @version 2016年5月10日 下午3:08:01
	 */
	void editUserpwd(String username, String oldUserpwd, String newUserpwd) throws HttpException;




	/**   
	 * 加密密码
	 * @author 范子才
	 * @param user
	 * @return
	 * @version 2017年3月10日 下午8:55:24
	 */
	String encryptPwd(String password, String username);
	
	/**   
	 * 通过Users产生token
	 * @author 范子才
	 * @param user
	 * @return
	 * @version 2017年3月10日 下午8:55:24
	 */
	String tokenFromUser(Users user);

	/**   
	 * 添加user
	 * @author 范子才
	 * @param user
	 * @return
	 * @version 2017年4月16日 下午1:16:34
	 * @throws HttpException 状态码409 用户名已存在
	 */
	Map<String, Object> add(Users user) throws HttpException;
}
