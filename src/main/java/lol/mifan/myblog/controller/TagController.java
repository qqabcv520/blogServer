/**   
 * filename：TagController.java
 *   
 * date：2017年3月22日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lol.mifan.myblog.resolver.HttpException;
import lol.mifan.myblog.service.TagService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/tags", produces="application/json;charset=UTF-8")
public class TagController {

	@Resource
    private TagService tagService;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getList(Integer offset, Integer limit, String query) throws HttpException {
        return tagService.getList(offset, limit, query);
    }
	
	@ResponseBody
	@RequestMapping(value = "/{tagId}/articles", method = RequestMethod.GET)
    public List<Map<String, Object>> getArticles(@PathVariable("tagId")int tagId, Integer offset, Integer limit, String query) throws HttpException {
        return tagService.getArticles(tagId, offset, limit, query);
    }
	
	
}
