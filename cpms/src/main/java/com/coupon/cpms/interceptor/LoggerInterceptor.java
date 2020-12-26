/**
 * 
 */
package com.coupon.cpms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author admin-PC
 *
 */

public class LoggerInterceptor implements AsyncHandlerInterceptor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.debug("===============================================");
		logger.debug("==================== BEGIN ====================");
		logger.debug("Request URI ===> " + request.getRequestURI());
		
		return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.debug("==================== END ======================");
		logger.debug("===============================================");
		
		AsyncHandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

}
