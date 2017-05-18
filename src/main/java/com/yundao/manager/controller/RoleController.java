package com.yundao.manager.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundao.manager.entity.rbac.Menu;
import com.yundao.manager.entity.rbac.Operate;
import com.yundao.manager.entity.rbac.Role;
import com.yundao.manager.service.MenuService;
import com.yundao.manager.service.OperateService;
import com.yundao.manager.service.RoleService;

import framework.page.Pager;
import framework.page.SearchFilter;
import framework.util.JsonDataGridHelper;
import framework.util.ResponseUtils;
import framework.util.Servlets;
import framework.validations.annotation.RequiredFieldValidator;
import framework.validations.annotation.RequiredStringValidator;
import framework.validations.annotation.Validations;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: RoleController
 * @Description: 角色模块
 * @author: zhaopo
 * @date: 2016年11月4日 下午3:05:41
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OperateService operateService;

	/**
	 * 
	 * @Title: tree
	 * @Description: 树的页面
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree() {
		return "/admin/role/tree";
	}

	/**
	 * 
	 * @Title: getPrivTree
	 * @Description: 获取模块树
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping("/getPrivTree")
	@ResponseBody
	public String getPrivTree(HttpServletResponse response) {
		List<Menu> menus = menuService.getAllMenus();
		List<Operate> operates = roleService.getAutOperate(null);

		JSONArray jarray = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("name", "权限树");
		obj.put("open", true);
		obj.put("idKey", "m" + null);
		obj.put("pIdKey", null);
		jarray.add(obj);

		if (menus != null || operates != null) {
			for (int i = 0; i < menus.size(); i++) {
				JSONObject jObj = menuService.getJson(menus.get(i));
				jObj.put("open", true);
				jarray.add(jObj);
			}

			for (int i = 0; i < operates.size(); i++) {
				JSONObject jObj = operateService.getJson(operates.get(i));
				jarray.add(jObj);
			}
		}

		return ResponseUtils.renderJSON(jarray, response);
	}

	/**
	 * 
	 * @Title: list
	 * @Description: 列表
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "/admin/role/list";
	}

	/**
	 * 
	 * @Title: queryData
	 * @Description: 查询数据
	 * @param pager
	 * @param request
	 * @return
	 * @return: Pager
	 */
	@RequestMapping("/queryData")
	@ResponseBody
	public Pager queryData(Pager pager, HttpServletRequest request) {
		Map<String, Object> filterParams = Servlets.getParametersStartingWith(request);
		List<SearchFilter> filters = SearchFilter.parse(filterParams);

		pager = roleService.find(pager, filters);
		return JsonDataGridHelper.createJSONData(pager);
	}

	/**
	 * 
	 * @Title: input
	 * @Description:新增页面
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String input() {
		return "/admin/role/input";
	}

	/**
	 * 
	 * @Title: edit
	 * @Description: 修改页面
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "/admin/role/input";
	}

	/**
	 * 
	 * @Title: priv
	 * @Description: 获取角色的资源和操作
	 * @param id
	 * @return
	 * @return: JSONObject
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "id", message = "id非法!") })
	@RequestMapping("/priv")
	@ResponseBody
	public JSONObject priv(Long id) {
		Long[] menuIds = null;
		Long[] operateIds = null;
		Role role = roleService.queryById(id);

		List<Menu> menus = menuService.getMenus(id);
		if (menus != null && menus.size() > 0) {
			Set<Menu> menuSet = new HashSet<Menu>(menus);
			menuIds = new Long[menuSet.size()];
			int i = 0;
			for (Iterator<Menu> iterator = menuSet.iterator(); iterator.hasNext();) {
				Menu menu = (Menu) iterator.next();
				menuIds[i] = menu.getId();
				i++;
			}
		}
		List<Operate> operates = operateService.getOperates(id);
		if (operates != null && operates.size() > 0) {
			Set<Operate> operateSet = new HashSet<Operate>(operates);
			operateIds = new Long[operateSet.size()];
			int i = 0;
			for (Iterator<Operate> iterator = operateSet.iterator(); iterator.hasNext();) {
				Operate operate = (Operate) iterator.next();
				operateIds[i] = operate.getId();
				i++;
			}
		}

		JSONObject jobj = new JSONObject();
		jobj.put("menuIds", menuIds);
		jobj.put("operateIds", operateIds);
		return jobj;
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除的时候注意级联删除
	 * @param id
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "id", message = "id非法!") })
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(Long id, HttpServletResponse response) {
		roleService.deleteById(id);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: deleteRole
	 * @Description: 删除
	 * @param ids
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "ids", message = "ids非法!") })
	@RequestMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(Long[] ids, HttpServletResponse response) {
		roleService.remove(ids);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @param role
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "name", message = "角色名称不能为空!"), @RequiredStringValidator(fieldName = "value", message = "角色标识不能为空!") })
	@RequestMapping("/save")
	@ResponseBody
	public String save(Role role, HttpServletResponse response) {
		// 这边保存只做保存的操作 权限管理这边全部放在绑定菜单那边
		
		roleService.save(role);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 更新
	 * @param role
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "id", message = "ID不能为空!") }, requiredStrings = { @RequiredStringValidator(fieldName = "name", message = "名称不能为空!"),
			@RequiredStringValidator(fieldName = "value", message = "角色标识不能为空!") })
	@RequestMapping("/update")
	@ResponseBody
	public String update(Role role, HttpServletResponse response) {
		 roleService.updateSelective(role);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: editRoleMenu
	 * @Description: 修改
	 * @param id
	 * @param menuIds
	 * @param operateIds
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "id", message = "角色ID不能为空!"), @RequiredFieldValidator(fieldName = "menuIds", message = "菜单ID不能为空!"),
			@RequiredFieldValidator(fieldName = "operateIds", message = "操作ID不能为空!") })
	@RequestMapping("/editRoleMenu")
	@ResponseBody
	public String editRoleMenu(Long id, Long[] menuIds, Long[] operateIds, HttpServletResponse response) {
		Role role = roleService.queryById(id);
		roleService.editRoleMenu(role, menuIds, operateIds);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: checkName
	 * @Description: 是否已存在ajax验证
	 * @param role
	 * @param oldValue
	 * @return
	 * @return: String
	 */
	@RequestMapping("/checkName")
	@ResponseBody
	public Boolean checkName(Role role, String oldValue) {
		String newValue = role.getName();
		if (roleService.isUnique("name", oldValue, newValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkValue
	 * @Description: 是否已存在 ajax验证
	 * @param role
	 * @param oldValue
	 * @return
	 * @return: String
	 */
	@RequestMapping("/checkValue")
	@ResponseBody
	public Boolean checkValue(Role role, String oldValue) {
		String newValue = role.getValue();
		if (roleService.isUnique("value", oldValue, newValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: roleMenu
	 * @Description: 角色菜单页面
	 * @param isEdit
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping("/roleMenu")
	public String roleMenu(Boolean isEdit, Model model) {
		model.addAttribute("isEdit", isEdit);
		return "/admin/role/roleMenu";
	}

}
