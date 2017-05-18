package com.yundao.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yundao.manager.Constant;
import com.yundao.manager.controller.BaseController;

/**
 * 
 * @ClassName: ErrorInterceptor
 * @Description: 错误拦截器
 * @author: jinzhaopo
 * @date: 2015-5-11 上午11:17:12
 */
@Component
public class ErrorInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			Object obj = request.getAttribute(Constant.WEBERRORS);
			if (obj != null) {
				modelAndView.addObject(Constant.WEBERRORS, obj);// 其实这个object对象一班都是WEBERRORS
				modelAndView.setViewName(BaseController.WEBERRORS_URL);// 若有错误的话直接到错误页面
			}

		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
