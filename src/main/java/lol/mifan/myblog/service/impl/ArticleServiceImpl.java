/**   
 * filename：ArticleServiceImpl.java
 *   
 * date：2017年3月12日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lol.mifan.myblog.dao.ArticleDAO;
import lol.mifan.myblog.dao.TagDAO;
import lol.mifan.myblog.dao.UserDAO;
import lol.mifan.myblog.model.Article;
import lol.mifan.myblog.model.Tag;
import lol.mifan.myblog.model.Users;
import lol.mifan.myblog.resolver.HttpException;
import lol.mifan.myblog.service.ArticleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements
		ArticleService {
	
	@Autowired
	private ArticleDAO articleDao;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private TagDAO tagDao;


	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public Map<String, Object> get(Serializable id) throws HttpException {

		Article article = articleDao.getActive(id);
		
		if(article == null) {
			logger.info(id + "文章不存在");
			throw new HttpException(404, "文章不存在");
		}
		return bindArticle(article);
	}

	@Transactional(readOnly=true, isolation=Isolation.READ_COMMITTED)
	@Override
	public List<Map<String, Object>> getList(Integer offset, Integer limit, String query) throws HttpException {
		
		List<Article> articles = articleDao.getArticleList(limit, offset, query);
		
		return bindArticleList(articles);
	}

	@Transactional
	@Override
	public Map<String, Object> add(Article article) {
		
		article.setId(null);
		article.setCreateTime(new Date());
		article.setClickCnt(0);
		article.setDeleted(false);
		article.setSort(0);
		article.setUsers(userDao.findActiveByUsername("mifan"));
		
		Set<Tag> tags = article.getTags();
		
		
		for(Tag tag : tags) {
			Tag t = tagDao.findTagByName(tag.getName());
			if(t == null) {
				tag.setId(null);
				tagDao.save(tag);
			} else {
				tag.setId(t.getId());
			}
		}
		articleDao.save(article);
		
		
		
		return bindArticle(article);
	}
	
	private HashMap<String, Object> bindArticle(Article article) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", article.getId());
		map.put("title", article.getTitle());
		map.put("outline", article.getOutline());
		map.put("content", article.getContent());
		map.put("reprintedFrom", article.getReprintedFrom());
		map.put("create", article.getCreateTime());
		map.put("titleImg", article.getTitleImg());
		
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
		
		return map;
	}
	private List<Map<String, Object>> bindArticleList(List<Article> articles) {

		List<Map<String, Object>> list = new ArrayList<>();
		for(Article article : articles) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", article.getId());
			map.put("title", article.getTitle());
			map.put("outline", article.getOutline());
			map.put("reprintedFrom", article.getReprintedFrom());
			map.put("create", article.getCreateTime());
			map.put("titleImg", article.getTitleImg());
			
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
