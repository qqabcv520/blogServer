/**   
 * filename：UserDAO.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.dao;


import lol.mifan.myblog.model.Users;




public interface UserDAO extends BaseDAO<Users> {

	/**   
	 * 
	 * @author 范子才
	 * @param username
	 * @return
	 * @version 2016年8月10日 下午1:28:12
	 */
	Users findActiveByUsername(String username);


	
}
