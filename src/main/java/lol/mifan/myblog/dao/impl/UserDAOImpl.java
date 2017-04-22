/**   
 * filename：UserDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.dao.impl;


import lol.mifan.myblog.dao.UserDAO;
import lol.mifan.myblog.model.Users;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;



@Repository("userDao")
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO {

	@Override
	public Users findActiveByUsername(String username) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("deleted", false));
		criteria.add(Restrictions.eq("username", username));
		return findEntityByDetachedCriteria(criteria);
		
	}


}