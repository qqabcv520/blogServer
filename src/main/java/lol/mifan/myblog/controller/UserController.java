/**   
 * filename：UserController.java
 *   
 * date：2017年3月9日
 * Copyright reey Corporation 2017 
 *   
 */
package lol.mifan.myblog.controller;


import java.util.Map;

import javax.annotation.Resource;

import lol.mifan.myblog.model.Users;
import lol.mifan.myblog.resolver.HttpException;
import lol.mifan.myblog.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users", produces="application/json;charset=UTF-8")
public class UserController {

	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.POST)
    public Map<String, Object> token(@RequestBody Users user) throws HttpException{
        return userService.token(user.getUsername(), user.getPassword());
    }
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> post(@RequestBody Users user) throws HttpException{
        return userService.add(user);
    }
	
	
}
