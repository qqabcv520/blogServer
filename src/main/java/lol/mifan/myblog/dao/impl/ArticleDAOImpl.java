/**   
 * filename：ArticleDAOImpl.java
 *   
 * date：2017年3月12日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import lol.mifan.myblog.dao.ArticleDAO;
import lol.mifan.myblog.model.Article;


@Repository("articleDao")
public class ArticleDAOImpl extends BaseDAOImpl<Article> implements ArticleDAO {

	@Override
	public List<Article> getArticleList(Integer limit, Integer offset, String query) {
		
		DetachedCriteria criteria =  createDetachedCriteria();
		criteria.add(Restrictions.eq("deleted", false));
		criteria.addOrder(Order.desc("sort"));
		return findEntitysByDetachedCriteria(criteria, limit, offset);
	}

	@Override
	public List<Article> getArticlesByTag(int tagId, Integer limit,
			Integer offset, String query) {
		DetachedCriteria criteria =  createDetachedCriteria();

		criteria.createAlias("tags", "tags", JoinType.RIGHT_OUTER_JOIN);
        Property tempTagId = Property.forName("tags.id");
        criteria.add(tempTagId.eq(tagId));
		
		
		criteria.add(Restrictions.eq("deleted", false));


		criteria.addOrder(Order.desc("sort"));
		return findEntitysByDetachedCriteria(criteria, limit, offset);
	}
	
	
	
	
}
