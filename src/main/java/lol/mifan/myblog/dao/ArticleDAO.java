/**   
 * filename：ArticleDAO.java
 *   
 * date：2017年3月12日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.dao;

import java.util.List;

import lol.mifan.myblog.model.Article;

public interface ArticleDAO extends BaseDAO<Article> {

	/**   
	 * 获取文章列表
	 * @author 范子才
	 * @param limit
	 * @param offset
	 * @param query
	 * @return
	 * @version 2017年3月22日14:33:09
	 */
	List<Article> getArticleList(Integer limit, Integer offset, String query);
	
	/**   
	 * 获取标签包含的文章列表
	 * @author 范子才
	 * @param tagId
	 * @param limit
	 * @param offset
	 * @param query
	 * @return
	 * @version 2017年4月12日 下午2:38:04
	 */
	List<Article> getArticlesByTag(int tagId, Integer limit, Integer offset, String query);
	
}
