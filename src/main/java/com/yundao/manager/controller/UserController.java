package com.yundao.manager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundao.manager.entity.rbac.Role;
import com.yundao.manager.entity.rbac.User;
import com.yundao.manager.service.RoleService;
import com.yundao.manager.service.UserService;

import framework.page.Message;
import framework.page.Pager;
import framework.page.WebErrors;
import framework.util.JsonDataGridHelper;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;
import framework.util.Servlets;

/**
 * 
 * @ClassName: UserController
 * @Description: 用户管理
 * @author: zhaopo
 * @date: 2016年11月16日 上午10:58:21
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @Title: list
	 * @Description: 用户列表
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "/admin/user/list";
	}

	/**
	 * 
	 * @Title: queryData
	 * @Description: 数据查询
	 * @param pager
	 * @param request
	 * @return
	 * @return: Pager
	 */
	@RequestMapping(value = "/queryData", method = RequestMethod.POST)
	@ResponseBody
	public Pager queryData(Pager pager, HttpServletRequest request) {
		Map<String, Object> filterParams = Servlets.getParametersStartingWith(request);
		// List<SearchFilter> filters = SearchFilter.parse(filterParams);
		// pager = userService.find(pager, filters);
		pager = userService.findByXml(pager, filterParams);

		return JsonDataGridHelper.createJSONData(pager);
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加页面
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("allRoles", roleService.queryAll());
		return "/admin/user/input";
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加用户（这边的roleId只允许为一个）
	 * @param user
	 * @param repassword
	 * @param roleIds
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(User user, String repassword, Long roleId, HttpServletResponse response, HttpServletRequest request) {
		WebErrors errors = validateSave(user);
		if (errors.hasErrors()) {
			return errors.showErrorAjax(response);
		}
		user.setRoleId(roleId);
		String password = user.getPassword();
		if (StringUtils.isNotEmpty(password)) {
			if (!password.equals(repassword)) {
				errors.addErrorCode("admin.user.save.passwordNotValid");
				return errors.showErrorAjax(response);
			}
			user.setPassword(DigestUtils.md5Hex(password));
		}
		user.setDeptId(Long.valueOf(RequestUtils.getQueryParam(request, "dept.id")));
		user.setRealName(RequestUtils.getQueryParam(request, "real_name"));
		user.setRoleId(roleId);
		user.setIsAccountEnabled(true);
		user.setIsAccountLocked(false);
		user.setLoginCount(0);
		user.setLoginFailureCount(0);
		userService.save(user);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 更新
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model) {
		User s_user = userService.queryById(id);
		Role role = roleService.queryById(s_user.getRoleId());
		model.addAttribute("s_user", s_user);
		model.addAttribute("role", role);
		model.addAttribute("allRoles", roleService.queryAll());
		return "/admin/user/input";
	}

	/**
	 * 
	 * @Title: update
	 * @Description: TODO
	 * @param user
	 * @param roleIds
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(User user, Long roleId, HttpServletResponse response, HttpServletRequest request) {
		user.setRoleId(roleId);

		User persistent = userService.queryById(user.getId());
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(user.getPassword());
			user.setPassword(passwordMd5);
		} else {
			user.setPassword(persistent.getPassword());
		}

		if (persistent.getIsAccountLocked() && !user.getIsAccountLocked()) {
			user.setLoginFailureCount(0);
			user.setLockedDate(null);
		} else {
			user.setIsAccountLocked(persistent.getIsAccountLocked());
			user.setLoginFailureCount(persistent.getLoginFailureCount());
			user.setLockedDate(persistent.getLockedDate());
		}

		user.setDeptId(Long.valueOf(RequestUtils.getQueryParam(request, "dept.id")));
		user.setRealName(RequestUtils.getQueryParam(request, "real_name"));
		user.setRoleId(roleId);

		userService.updateSelective(user);
		return ResponseUtils.renderJSON(user, response);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param ids
	 * @return
	 * @return: Message
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Message delete(Long ids) {
		userService.deleteById(ids);
		return successMessage;
	}

	// ajax验证当前密码是否正确
	@RequestMapping(value = "/checkCurrentPassword", method = RequestMethod.POST)
	@ResponseBody
	public String checkCurrentPassword(String currentPassword, HttpServletResponse response) {
		User admin = getCurrentUser();
		if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), admin.getPassword())) {
			return ResponseUtils.renderJSON("true", response);
		} else {
			return ResponseUtils.renderJSON("false", response);
		}
	}

	/**
	 * 
	 * @Title: updateProfile
	 * @Description: 更新个人资料
	 * @param oldPassword
	 * @param currentPassword
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	@ResponseBody
	public String updateProfile(String oldPassword, String currentPassword, HttpServletResponse response) {
		User persistent = getCurrentUser();
		if (persistent == null) {
			// addActionError("登录超时，请先登录!");
			return WEBERRORS_URL;
		}
		if (StringUtils.isNotEmpty(oldPassword) && StringUtils.isNotEmpty(persistent.getPassword())) {
			if (!StringUtils.equals(DigestUtils.md5Hex(oldPassword), persistent.getPassword())) {
				WebErrors errors = WebErrors.create();
				errors.addError("当前密码不正确!");
				return errors.showErrorAjax(response);
			}
		}
		persistent.setPassword(DigestUtils.md5Hex(currentPassword));
		userService.update(persistent);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: validateSave
	 * @Description: 验证用户参数
	 * @param bean
	 * @return
	 * @return: WebErrors
	 */
	private WebErrors validateSave(User bean) {
		WebErrors errors = WebErrors.create();
		if (!userService.isUnique("userName", null, bean.getUserName())) {
			errors.addErrorCode("error.unique", "userName");
		}
		return errors;
	}

	/**
	 * 
	 * @Title: checkUsername
	 * @Description: 是否已存在 ajax验证
	 * @param username
	 * @param oldValue
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkUsername(String userName, String oldValue, HttpServletResponse response) {
		if (userService.isUnique("userName", oldValue, userName)) {
			return true;
		} else {
			return false;
		}
	}
}
