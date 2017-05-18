package com.yundao.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gaiya.ceo.model.manager.User;
import com.gaiya.ceo.model.rbac.Menu;
import com.gaiya.ceo.model.rbac.Operate;
import com.gaiya.ceo.service.MenuService;
import com.gaiya.ceo.service.OperateService;

import framework.mvc.Message;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;
import framework.validations.annotation.RequiredFieldValidator;
import framework.validations.annotation.Validations;

/**
 * 
 * @ClassName: OperateController
 * @Description: 操作
 * @author: zhaopo
 * @date: 2016年11月4日 下午3:08:10
 */
@Controller
@RequestMapping("/admin/operate")
public class OperateController extends BaseController {

	@Autowired
	private OperateService operateService;

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/toolBarButton", method = RequestMethod.POST)
	@ResponseBody
	public List<Operate> toolBarButton(HttpServletRequest request, HttpServletResponse response) {
		String url = RequestUtils.getQueryParam(request, "url");

		Operate operate = operateService.getBestFitOperate(url);
		if (operate.getMenuId() == null) {
			return null;
		}

		Long roleId = null;
		User cur_user = getCurrentUser();
		if (cur_user != null && !cur_user.isSystemManager()) {
			roleId = cur_user.getRoleId();
		}

		List<Operate> privOperates = operateService.getPrivOperates(roleId, operate.getMenuId(), true);
		return privOperates;
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加页面
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long id, Model model) {
		Menu menu = menuService.queryById(id);
		model.addAttribute("menu", menu);
		return "/admin/menu/operateAdd";
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 保存
	 * @param operate
	 * @param menu_id
	 * @param redirectattributes
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Operate operate, Long menu_id, RedirectAttributes redirectattributes) {
		operate.setMenuId(menu_id);
		if (!validate(operate)) {
			return "/admin/common/error";
		} else {
			operateService.save(operate);
			redirect(redirectattributes, successMessage);
			return "redirect:/admin/menu/detail.jhtml?id=" + menu_id;
		}
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改页面
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model) {
		Operate operate = operateService.queryById(id);
		model.addAttribute("operate", operate);
		model.addAttribute("menu", menuService.queryById(operate.getMenuId()));
		return "/admin/menu/operateAdd";
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param operate
	 * @param menu_id
	 * @param redirectattributes
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Operate operate, Long menu_id, RedirectAttributes redirectattributes) {
		operate.setMenuId(menu_id);
		if (!validate(operate)) {
			return "/admin/common/error";
		} else {
			operateService.updateSelective(operate);
			redirect(redirectattributes, successMessage);
			return "redirect:/admin/menu/detail.jhtml?id=" + menu_id;
		}
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: Message
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public Message delete(@PathVariable Long id) {
		operateService.deleteById(id);
		return successMessage;
	}

	/**
	 * 
	 * @Title: updateOperatePriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "ids", message = "ID非法!"), @RequiredFieldValidator(fieldName = "priority", message = "排序非法!") })
	@RequestMapping(value = "/updateOperatePriority", method = RequestMethod.POST)
	public String updateOperatePriority(Long[] ids, Integer[] priority, HttpServletResponse response) {
		if (ids.length != priority.length) {
			addActionError("ids长度与排序的长度不一致!");
			return ERROR_PAGE;
		}
		operateService.updatePriority(ids, priority);
		return ResponseUtils.renderJSON(successMessage, response);
	}

}
