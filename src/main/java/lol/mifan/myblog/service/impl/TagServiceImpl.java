/**   
 * filename：TagServiceImpl.java
 *   
 * date：2017年3月22日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lol.mifan.myblog.dao.ArticleDAO;
import lol.mifan.myblog.dao.TagDAO;
import lol.mifan.myblog.model.Article;
import lol.mifan.myblog.model.Tag;
import lol.mifan.myblog.model.Users;
import lol.mifan.myblog.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Resource
	private TagDAO tagDao;

	@Resource
	private ArticleDAO articleDao;

	private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> get(Serializable id) {
		Tag tag = tagDao.getActive(id);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(tag != null) {
			map.put("id", tag.getId());
			map.put("name", tag.getName());
		}
		return map;
	}


	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public List<Map<String, Object>> getList(Integer offset, Integer limit, String query) {
		
		List<Tag> tags = tagDao.getTagList(limit, offset, query);
		List<Map<String, Object>> list = new ArrayList<>();
		for(Tag tag : tags) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", tag.getId());
			map.put("name", tag.getName());
			list.add(map);
		}
		
		return list;
	}



	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public List<Map<String, Object>> getArticles(int tagId, Integer offset, Integer limit,
			String query) {
		List<Article> articles = articleDao.getArticlesByTag(tagId, limit, offset, query);


		List<Map<String, Object>> list = new ArrayList<>();
		for(Article article : articles) {

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", article.getId());
			map.put("title", article.getTitle());
			map.put("outline", article.getOutline());
			map.put("reprintedFrom", article.getReprintedFrom());
			map.put("create", article.getCreateTime());

			List<Map<String, Object>> tags = new ArrayList<>();
			for(Tag tag : article.getTags()) {
				if(!tag.isDeleted()) {
					Map<String, Object> tagMap = new HashMap<>();
					tagMap.put("id", tag.getId());
					tagMap.put("name", tag.getName());
					tags.add(tagMap);
				}
			}
			map.put("tags", tags);
			
			Users user = article.getUsers();
			if(user != null && !user.isDeleted()) {
				map.put("author", user.getUsername());
			}
			
			list.add(map);
		}
		
		return list;
	}

}
