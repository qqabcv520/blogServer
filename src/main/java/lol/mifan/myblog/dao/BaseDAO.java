/**   
 * filename：GeneralDAO.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import org.hibernate.criterion.DetachedCriteria;

public interface BaseDAO<E> {

	/**
	 * 保存/更新
	 * @param entity
	 * @throws ValidateException 
	 */
	void saveOrUpdate(E entity);
	
	/**
	 * 获取对应实体信息
	 * @param id 主键
	 * @return
	 */
	E get(Serializable id);
	

	/**
	 * 实体保存
	 * @param entity
	 * @throws Exception
	 */
	void save(E entity);
	
	/**
	 * 实体更新
	 * @param entity
	 * @throws Exception
	 */
	void update(E entity);
	
	/**
	 * 彻底删除，不建议使用
	 * @param entity 要删除的实体类
	 */
	void delete(E entity);
	
	
	/**
	 * 获取全部实体信息
	 * @return
	 */
	List<E> getAll();
	
	
	/**
	 * hql查询数据
	 * @param HQL 其中的参数写法为  :parameterName
	 * @param parameters key为HQL参数名，value为HQL参数值
	 * @return
	 */
	List<E> findEntitysByHQL(String hql, Map<String, Object> parameters);
	
	/**
	 * hql查询数据
	 * @param HQL 其中的参数写法为  :parameterName
	 * @param parameters key为HQL参数名，value为HQL参数值
	 * @return
	 */
	E findEntityByHQL(String hql, Map<String, Object> parameters);
	/**
	 * hql查询数据
	 * @param HQL 其中的参数写法为  :parameterName
	 * @param parameters key为HQL参数名，value为HQL参数值
	 * @return
	 */
	Object findByHQL(String hql, Map<String, Object> parameters);
	

	/**
	 * 根据入参参数类型获取到复合条件的所有实体信息list集合
	 * @param condition
	 * @return
	 */
	List<E> findEntitysByDetachedCriteria(DetachedCriteria condition);

	/**
	 * 根据入参参数类型获取到复合条件的所有实体信息list集合
	 * @param condition
	 * @param limit
	 * @param offset
	 * @return
	 */
	List<E> findEntitysByDetachedCriteria(DetachedCriteria condition, Integer limit, Integer offset);
	
	/**
	 * 根据入参参数类型获取到复合条件的所有实体信息
	 * @param condition
	 * @return
	 */
	E findEntityByDetachedCriteria(DetachedCriteria condition);
	/**
	 * 根据入参参数类型获取到复合条件的所有实体信息条数
	 * @param condition
	 * @return
	 */
	int getRowCountByDetachedCriteria(DetachedCriteria condition);

	

	/**   
	 * 根据列名和值获实体
	 * @author 范子才
	 * @param ColumnName
	 * @param value
	 * @return
	 * @version 2016年7月22日 上午12:09:28
	 */
	E findEntityByColumn(String ColumnName, Object value);
	
	/**   
	 * 批量删除
	 * @author 范子才
	 * @param idArray
	 * @return 改变的数据数
	 * @version 2016年4月24日 下午10:28:52
	 */
	int deleteByIdArray(Serializable[] idArray);
	
	
	/**
	 * 逻辑删除，入参为id主键
	 * @param id
	 * @throws Exception
	 */
	void tombstonedById(Serializable id);
	
	/**   
	 * 批量逻辑删除，入参为id主键数组
	 * @author 范子才
	 * @param idArr
	 * @return JSON
	 * @throws Exception
	 * @version 2016年4月25日 上午10:46:04
	 */
	int tombstonedByIdArray(Serializable[] idArr);
	
	
	
	/**
	 * 获取对应实体信息，包含已经逻辑删除的对象
	 * @param id 主键
	 * @return
	 */
	E getActive(Serializable id);
	
	
	
	/**
	 * 获取全部实体信息
	 * @return
	 */
	List<E> getActiveAll();
	
	
	
	/**   
	 * 根据列名和值获活动的实体
	 * @author 范子才
	 * @param ColumnName
	 * @param value
	 * @return
	 * @version 2016年7月22日 上午12:09:28
	 */
	E findActiveByColumn(String ColumnName, Object value);
	
	
	/**   
	 * 根据泛型类创建一个DetachedCriteria
	 * @author 范子才
	 * @return
	 * @version 2017年3月12日 下午7:37:35
	 */
	DetachedCriteria createDetachedCriteria();
	
	/**   
	 * 获取泛型主键名
	 * @author 范子才
	 * @return
	 * @version 2016年4月29日 下午12:04:45
	 */
	String getPkName();

	/**   
	 * 判断实体类是否存在指定字段
	 * @author 范子才
	 * @param name
	 * @return
	 * @version 2016年5月29日 下午3:09:08
	 */
	boolean hasPropertyName(String name);
}