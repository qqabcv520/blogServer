/**   
 * filename：TagService.java
 *   
 * date：2017年3月22日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lol.mifan.myblog.resolver.HttpException;

public interface TagService {

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
	 * 分页取tag对应的ArtivcleList
	 * @author 范子才
	 * @param tagId 
	 * @param offset
	 * @param limit
	 * @param query
	 * @return
	 * @version 2017年4月12日 下午1:38:54
	 */
	List<Map<String, Object>> getArticles(int tagId, Integer offset, Integer limit,
			String query);
	
	
}
