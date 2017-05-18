package com.yundao.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yundao.manager.Constant;
import com.yundao.manager.entity.rbac.User;

import framework.util.ApplicationContext;

/**
 * 
 * @ClassName: AdminDetailsInterceptor
 * @Description: 登入拦截器
 * @author: jinzhaopo
 * @date: 2015-5-21 下午3:31:29
 */
@Component
public class AdminDetailsInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object obj = ApplicationContext.getSessionAttr(Constant.CURRENT_ADMIN);
		if (obj == null) {
			response.sendRedirect((new StringBuilder(request.getContextPath())).append(Constant.LOGINURL).toString());
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String s = modelAndView.getViewName();
			if (!StringUtils.startsWith(s, "redirect:"))
				modelAndView.addObject("user", (User) ApplicationContext.getSessionAttr(Constant.CURRENT_ADMIN));
		}
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
