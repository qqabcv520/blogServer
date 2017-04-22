/**   
 * filename：ExceptionHandler.java
 *   
 * date：2016年5月11日
 * Copyright reey Corporation 2016 
 *   
 */
package lol.mifan.myblog.resolver;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		
		
		HandlerMethod method = (HandlerMethod)handler;  

		response.setContentType("application/json;charset=UTF-8");
		HashMap<String, Object> map = new HashMap<>();
		if(e instanceof HttpException) {
			response.setStatus(((HttpException) e).getCode());
			map.put("error", e.getMessage());
			
		} else {
			response.setStatus(500);
			map.put("error", "服务器异常");
			e.printStackTrace();
			logger.error(method.toString() + " - " + e.toString());
		}
		
		try {
			response.getWriter().print(objectMapper.writeValueAsString(map));
		} catch (IOException e1) {
			response.setStatus(500);
			logger.error(e1.getMessage());
		}
		return new ModelAndView();
	}

}
