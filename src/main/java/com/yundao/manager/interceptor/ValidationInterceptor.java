package com.yundao.manager.interceptor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yundao.manager.Constant;

import framework.page.WebErrors;
import framework.plugin.IPlugin;
import framework.validations.IValidate;
import framework.validations.ValidationBundle;
import framework.validations.annotation.Validations;

/**
 * 
 * @ClassName: ValidationInterceptor
 * @Description: 验证拦截器
 * @author: jinzhaopo
 * @date: 2015-5-11 上午11:16:52
 */
@Component
public class ValidationInterceptor implements HandlerInterceptor {
	@Resource
	private ValidationBundle validationBundle;

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		WebErrors errors = WebErrors.create();
		Map<String, String> decodedUriVariables = null;
		HandlerMethod hm = (HandlerMethod) handler;
		Validations validations = hm.getMethodAnnotation(Validations.class);
		if (validations == null) {
			return true;
		}
		Object obj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if (obj != null) {
			decodedUriVariables = (Map<String, String>) obj;
		}
		List<IPlugin> pluginList = validationBundle.getPluginList();
		for (IPlugin plugin : pluginList) {
			IValidate validate = (IValidate) plugin;
			errors = validate.validate(request, errors, validations, decodedUriVariables);
		}
		if (errors.hasErrors()) {
			String xRequestedWith = request.getHeader("X-Requested-With");
			if (xRequestedWith != null && xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
				errors.showErrorAjax(response);
			} else {
				request.setAttribute(Constant.WEBERRORS, errors);
				request.getRequestDispatcher(Constant.WEBERRORS_URL_REQUEST).forward(request, response);
			}
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println();
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}
