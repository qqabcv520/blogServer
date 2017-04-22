/**   
 * filename：BaseDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lol.mifan.myblog.dao.BaseDAO;
import lol.mifan.myblog.util.MyUtils;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Repository;



@Repository("baseDao")
public class BaseDAOImpl<E> implements BaseDAO<E> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<E> persistentClass;
	
	@SuppressWarnings("unchecked")
	public BaseDAOImpl(){  
	    //getClass() 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的超类的 Class。  
	    this.persistentClass = (Class<E>)MyUtils.getSuperClassGenricType(getClass(), 0);  
	}
	

	@Override
	public void saveOrUpdate(E entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(Serializable id) {
		return (E) sessionFactory.getCurrentSession().get(persistentClass, id);
	}

	@Override
	public void save(E entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void update(E entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public void delete(E entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public List<E> getAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		return findEntitysByDetachedCriteria(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public E findEntityByHQL(String hql, Map<String, Object> parameters) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
		}
		return (E) query.uniqueResult();
	}

	@Override
	public Object findByHQL(String hql, Map<String, Object> parameters) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
		}
		return query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findEntityByDetachedCriteria(DetachedCriteria condition) {
		
		Criteria criteria = condition.getExecutableCriteria(sessionFactory.getCurrentSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return (E) criteria.uniqueResult();
	}

	@Override
	public int getRowCountByDetachedCriteria(DetachedCriteria condition) {
		Criteria criteria = condition.getExecutableCriteria(sessionFactory.getCurrentSession());
		Long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return totalCount == null ? 0 : totalCount.intValue();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<E> findEntitysByHQL(String hql, Map<String, Object> parameters) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findEntitysByDetachedCriteria(DetachedCriteria condition) {
		Criteria criteria = condition.getExecutableCriteria(sessionFactory.getCurrentSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findEntitysByDetachedCriteria(DetachedCriteria condition,
			Integer limit, Integer offset) {
		Criteria criteria = condition.getExecutableCriteria(sessionFactory.getCurrentSession());
		if(limit != null) {
			criteria.setMaxResults(limit);
		}
		if(offset != null) {
			criteria.setFirstResult(offset);
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}


	@Override
	public int deleteByIdArray(Serializable[] idArray) {
		
		String queryString = "DELETE FROM " + persistentClass.getName() + " WHERE " + getPkName() + " IN :idArr";
		
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameterList("idArr", idArray);
		return query.executeUpdate();
	}




	@Override
	public E findEntityByColumn(String ColumnName, Object value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.add(Restrictions.eq(ColumnName, value));
		return findEntityByDetachedCriteria(criteria);
	}


	@Override
	public void tombstonedById(Serializable id) {
		String queryString = "UPDATE " + persistentClass.getName() + " SET deleted=1 WHERE " + getPkName() + " IN :idArr";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameter("idArr", id);
		query.executeUpdate();
	}

	@Override
	public int tombstonedByIdArray(Serializable[] idArray) {
		String queryString = "UPDATE " + persistentClass.getName() + " SET deleted=1 WHERE " + getPkName() + " IN :idArr";
		Query query = sessionFactory.getCurrentSession().createQuery(queryString);
		query.setParameterList("idArr", idArray);
		return query.executeUpdate();
	}


	@Override
	public E getActive(Serializable id) {
		return findActiveByColumn(getPkName(), id);
	}


	@Override
	public List<E> getActiveAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.add(Restrictions.eq("deleted", 0));
		return findEntitysByDetachedCriteria(criteria);
	}



	@Override
	public E findActiveByColumn(String ColumnName, Object value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		criteria.add(Restrictions.eq(ColumnName, value));
		criteria.add(Restrictions.eq("deleted", false));
		return findEntityByDetachedCriteria(criteria);
	}


	@Override
	public String getPkName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(persistentClass);  
		return meta.getIdentifierPropertyName();
	}

	@Override
	public boolean hasPropertyName(String name) {
		ClassMetadata meta = sessionFactory.getClassMetadata(persistentClass);
		
		String[] propertyNames = meta.getPropertyNames();
		
		for (String i : propertyNames) {
		    if (i != null && i.equals(name)) {
		        return true;
		    }
		}
		String pkName = meta.getIdentifierPropertyName();
		if (pkName != null && pkName.equals(name)) {
	        return true;
	    }
		
		return false;

		
	}


	@Override
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(persistentClass);
	}
}
