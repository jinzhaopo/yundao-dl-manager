package com.yundao.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @ClassName: IndexController
 * @Description: 首页
 * @author: zhaopo
 * @date: 2016年11月4日 下午2:35:18
 */
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/admin/common/index";
	}

	@RequestMapping(value = "/middle", method = RequestMethod.GET)
	public String middle() {
		return "/admin/common/middle";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "/admin/common/welcome";
	}
}
