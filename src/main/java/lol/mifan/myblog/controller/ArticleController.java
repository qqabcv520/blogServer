/**   
 * filename：ArticleController.java
 *   
 * date：2017年3月12日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lol.mifan.myblog.model.Article;
import lol.mifan.myblog.resolver.HttpException;
import lol.mifan.myblog.service.ArticleService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/articles", produces="application/json;charset=UTF-8")
public class ArticleController {

	
	@Resource
	private ArticleService articleService;
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, ?> get(@PathVariable("id")int id) throws HttpException {
        return articleService.get(id);
    }
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getList(Integer offset, Integer limit, String query) throws HttpException {
        return articleService.getList(offset, limit, query);
    }
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
    public Map<String, ?> post(@RequestBody Article article) throws HttpException {
        
        return articleService.add(article);
    }
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id")int id) throws HttpException {
        articleService.get(id);
    }
}
