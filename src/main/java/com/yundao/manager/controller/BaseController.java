package com.yundao.manager.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yundao.manager.Constant;
import com.yundao.manager.entity.rbac.User;

import framework.freemarker.directive.FlashMessageDirective;
import framework.page.Message;
import framework.page.WebErrors;
import framework.util.ApplicationContext;
import framework.util.SpringUtils;

public class BaseController {

	public static final String ADMIN_PATH = "/admin";// 请求路径的前缀
	public static final String WEBERRORS_URL = "/admin/common/error";// 公共的错误页面
	protected static final String ERROR_PAGE = "/admin/common/error";
	public static final String PAGER = "pager";// 页面pager对象的key
	protected static String ICON_PATH = "resource/thirdparty/ligerUI/skins/icons/32X32";

	@Resource
	private Validator validator;

	protected static Message warnMessage = Message.warn("yd.message.warn", new Object[0]);
	/**
	 * 错误信息
	 */
	protected static Message errorMessage = Message.error("yd.message.error", new Object[0]);
	/**
	 * 成功信息
	 */
	protected static Message successMessage = Message.success("yd.message.success", new Object[0]);

	/**
	 * 后台前缀
	 * 
	 */
	protected static final String ADMIN = "/admin";
	protected static final String CONSTRAINT_VIOLATIONS = "constraintViolations";

	// submit提交后，关闭当前的tab，并且刷新父页面
	protected static final String CLOSEANRELOAPARENT = "/admin/common/closeAndReloadParent";

	@SuppressWarnings("rawtypes")
	protected boolean validate(Object obj, final Class<?>... groups) {
		Set set = validator.validate(obj, groups);
		if (set.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestattributes = RequestContextHolder.currentRequestAttributes();
			requestattributes.setAttribute(CONSTRAINT_VIOLATIONS, set, 0);
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected boolean validate(Class beanType, String propertyName, Object value, final Class<?>... groups) {
		Set set = validator.validateValue(beanType, propertyName, value, groups);
		if (set.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestattributes = RequestContextHolder.currentRequestAttributes();
			requestattributes.setAttribute(CONSTRAINT_VIOLATIONS, set, 0);
			return false;
		}
	}

	/**
	 * 
	 * @Title: getMessage
	 * @Description: 获取国际化的配置
	 * @param code
	 * @param aobj
	 * @return
	 * @return: String
	 */
	protected String getMessage(String code, Object aobj[]) {
		return SpringUtils.getMessage(code, aobj);
	}

	/**
	 * 
	 * @Title: redirect
	 * @Description: 重定向
	 * @param redirectattributes
	 * @param message
	 * @return: void
	 */
	protected void redirect(RedirectAttributes redirectattributes, Message message) {
		if (redirectattributes != null && message != null) {
			redirectattributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}

	protected void addActionError(String message) {
		HttpServletRequest request = ApplicationContext.getRequest();
		WebErrors errors = (WebErrors) request.getAttribute("WebErrors");
		if (errors == null) {
			errors = WebErrors.create();
			request.setAttribute("WebErrors", errors);
		}
		errors.addErrorString(message);
	}

	protected WebErrors getErrors() {
		HttpServletRequest request = ApplicationContext.getRequest();
		WebErrors errors = (WebErrors) request.getAttribute("WebErrors");
		return errors;
	}

	protected void addActionError(String code, Object[] objects) {
		HttpServletRequest request = ApplicationContext.getRequest();
		WebErrors errors = (WebErrors) request.getAttribute("WebErrors");
		if (errors == null) {
			errors = WebErrors.create();
			request.setAttribute("WebErrors", errors);
		}
		errors.addErrorCode(code, objects);
	}

	/**
	 * 
	 * @Title: getCurrentUser
	 * @Description: 获取系统当前的用户
	 * @return
	 * @return: User
	 */
	public User getCurrentUser() {
		return (User) ApplicationContext.getSessionAttr(Constant.CURRENT_ADMIN);
	}

}
