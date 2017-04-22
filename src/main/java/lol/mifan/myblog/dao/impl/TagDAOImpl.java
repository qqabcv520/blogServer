/**   
 * filename：TagDAOImpl.java
 *   
 * date：2017年3月22日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import lol.mifan.myblog.dao.TagDAO;
import lol.mifan.myblog.model.Tag;

@Repository("tagDao")
public class TagDAOImpl extends BaseDAOImpl<Tag> implements TagDAO {

	@Override
	public List<Tag> getTagList(Integer limit, Integer offset, String query) {

		DetachedCriteria criteria =  createDetachedCriteria();
		criteria.add(Restrictions.eq("deleted", false));
		if(!StringUtils.isEmpty(query)) {
			criteria.add(Restrictions.like("name", query, MatchMode.ANYWHERE));
		}
		return findEntitysByDetachedCriteria(criteria, limit, offset);
	}

	@Override
	public Tag findTagByName(String name) {
		DetachedCriteria criteria = createDetachedCriteria();
		criteria.add(Restrictions.eq("deleted", false));
		criteria.add(Restrictions.eq("name", name));
		return findEntityByDetachedCriteria(criteria);
			
	}
	
	
}
