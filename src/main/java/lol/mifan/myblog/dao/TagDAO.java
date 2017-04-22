/**   
 * filename：TagDAO.java
 *   
 * date：2017年3月22日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.dao;

import java.util.List;

import lol.mifan.myblog.model.Tag;


public interface TagDAO extends BaseDAO<Tag> {
	
	/**   
	 * 获取标签列表
	 * @author 范子才
	 * @param limit 分页
	 * @param offset 分页
	 * @param query 搜索过滤name字段
	 * @return
	 * @version 2017年3月22日 下午3:41:14
	 */
	public List<Tag> getTagList(Integer limit, Integer offset, String query);

	/**   
	 * 通过标签名获取标签
	 * @author 范子才
	 * @param name
	 * @return
	 * @version 2017年4月11日 下午3:24:49
	 */
	public Tag findTagByName(String name);
	
	
}
