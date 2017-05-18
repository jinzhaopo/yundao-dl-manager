package com.yundao.manager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaiya.ceo.Constant;
import com.gaiya.ceo.model.manager.User;
import com.gaiya.ceo.service.MenuService;
import com.gaiya.ceo.service.MyCaptchaService;
import com.gaiya.ceo.service.RSAService;
import com.gaiya.ceo.service.UserService;

import framework.mvc.Message;
import framework.page.Order;
import framework.page.Order.Direction;
import framework.page.Pager;
import framework.page.SearchFilter;
import framework.util.AppContext;
import framework.util.JsonDataGridHelper;
import framework.util.RequestUtils;
import framework.util.Servlets;

/**
 * 管理后台登入控制器
 * 
 * @author jinzhaopo
 * @date 2016年7月4日上午9:21:25
 * @version 3.0.0
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {
	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private RSAService rSAService;

	@Autowired
	private MyCaptchaService myCaptchaService;

	/**
	 * 登入页面
	 * 
	 * @return 登入页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		// 在登录界面中生成captchaid
		String captchaId = UUID.randomUUID().toString();
		model.addAttribute("captchaId", captchaId);
		return ADMIN + "/common/login";
	}

	/**
	 * 
	 * @Title: login
	 * @Description: 登入
	 * @param user
	 * @return
	 * @return: String
	 */

	@RequestMapping(value = "/loginVerify", method = RequestMethod.POST)
	@ResponseBody
	public Message login(HttpServletRequest request, HttpServletResponse response) {
		// 判断验证码
		String captchaId = request.getParameter("captchaId");
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		boolean isCaptcha = validateCaptcha(request, captchaId);
		if (!isCaptcha) {
			warnMessage.setContent("验证码错误");
			return warnMessage;
		}
		String username = (String) request.getParameter("username");
		String enPassword = (String) request.getParameter("enPassword");
		User user = userService.get("userName", username);
		// 判断用户是否存在
		if (user != null) {
			if (!user.getIsAccountLocked()) {
				if (!user.getIsAccountEnabled()) {
					// 验证密码 先解密
					String password = rSAService.decryptParameter(enPassword);
					String md5_password = DigestUtils.md5Hex(password);
					if (md5_password.equals(user.getPassword())) {
						// 修改数据
						user.setLastLoginIp(RequestUtils.getIpAddr(request));
						user.setLoginCount(user.getLoginCount() + 1);
						user.setLastLoginTime(new Date());
						user.setLoginFailureCount(0);
						request.getSession().setAttribute(Constant.CURRENT_ADMIN, user);
						userService.update(user);
						return successMessage;
					} else {
						user.setLoginFailureCount(user.getLoginFailureCount() + 1);
						if (user.getLoginFailureCount() == 5) {
							// 锁定帐号
							user.setIsAccountLocked(true);
							user.setLockedDate(new Date());
							warnMessage.setContent("密码错误,帐号锁定");
						} else if (user.getLoginFailureCount() >= 2) {
							warnMessage.setContent("密码错误,你还有" + (5 - user.getLoginFailureCount()) + "次机会");
						} else {
							warnMessage.setContent("密码错误");
						}
						userService.update(user);

					}

				} else {
					warnMessage.setContent("账号已禁用");
				}
			} else {
				warnMessage.setContent("账号已锁定");
			}
		} else {
			warnMessage.setContent("用户名不存在");
		}
		return warnMessage;

	}

	/**
	 * 
	 * @Title: logOut
	 * @Description: 退出
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(Model model, HttpServletRequest request) {
		AppContext.removeAttribute(Constant.CURRENT_ADMIN);
		return login(model, request);
	}

	/**
	 * 校验验证码.
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * 
	 */
	protected boolean validateCaptcha(HttpServletRequest request, String captchaId) {
		String captcha = request.getParameter("captcha");
		return myCaptchaService.isValid(captchaId, captcha);
	}

	// 列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "/admin/test/list";
	}

	// 获取列表
	@RequestMapping(value = "/queryData", method = RequestMethod.POST)
	@ResponseBody
	public Pager queryData(Pager pager, HttpServletRequest request) {
		Map<String, Object> filterParams = Servlets.getParametersStartingWith(request);

		List<SearchFilter> searchFilters = SearchFilter.parse(filterParams);

		// 通过id升序排序
		List<Order> list = new ArrayList<Order>();
		list.add(new Order("id", Direction.desc));
		pager.setOrderList(list);
		// 查找并json格式返回数据
		// pager = testService.find(pager, searchFilters);

		return JsonDataGridHelper.createJSONData(pager);
	}

}
