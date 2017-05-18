package com.yundao.manager.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yundao.manager.Constant;
import com.yundao.manager.entity.enumModel.MenuType;
import com.yundao.manager.entity.rbac.Menu;
import com.yundao.manager.entity.rbac.Operate;
import com.yundao.manager.entity.rbac.User;
import com.yundao.manager.service.MenuService;
import com.yundao.manager.service.OperateService;
import com.yundao.manager.service.RoleMenuLinkService;

import framework.bean.FileWrap;
import framework.util.ApplicationContext;
import framework.util.PropertiesHelper;
import framework.util.RequestUtils;
import framework.util.ResponseUtils;
import framework.validations.annotation.RequiredFieldValidator;
import framework.validations.annotation.Validations;
import net.sf.json.JSONArray;

/**
 * 
 * @ClassName: MenuController
 * @Description: 菜单资源管理
 * @author: zhaopo
 * @date: 2016年11月4日 下午3:09:02
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private OperateService operateService;

	@Autowired
	private RoleMenuLinkService roleMenuLinkService;

	/**
	 * 
	 * @Title: manager
	 * @Description: 列表
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager() {
		return "/admin/menu/manager";
	}

	/**
	 * 
	 * @Title: detail
	 * @Description: 详细列表
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String detail(Long id, Model model) {
		// 获取根节点
		Menu menu = null;
		if (id == null) {
			menu = menuService.getRoot();
		} else {
			menu = menuService.queryById(id);
		}
		// 查询父菜单
		Menu parentMenu = null;
		if (menu.getUpMenuId() == null) {
			parentMenu = menuService.getRoot();
		} else {
			parentMenu = menuService.queryById(menu.getUpMenuId());
		}
		List<Menu> subMenuList = null;
		// 查询子菜单
		if (MenuType.MENUTYPE_PARENT.equals(menu.getType())) {
			if (menu.getId() == null) {
				subMenuList = menuService.getTopList();
			} else {
				subMenuList = menuService.getSubMenuList(menu.getId());
			}
		}
		List<Operate> normalActionList = null;
		List<Operate> authorActionList = null;
		// 查询动作
		if (MenuType.MENUTYPE_NODE.equals(menu.getType())) {
			normalActionList = operateService.getOperatesWidthCondition(menu.getId(), Constant.OPERATE_TYPE_NORMAL);

			authorActionList = operateService.getOperatesWidthCondition(menu.getId(), Constant.OPERATE_TYPE_AUTHORITY);
		}
		model.addAttribute("menu", menu);
		model.addAttribute("parentMenu", parentMenu);
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("isDelete", false);
		model.addAttribute("normalActionList", normalActionList);
		model.addAttribute("authorActionList", authorActionList);
		return "/admin/menu/detail";
	}

	/**
	 * 
	 * @Title: menuTree
	 * @Description: 资源树
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String menuTree() {
		return "/admin/menu/tree";
	}

	/**
	 * 
	 * @Title: treeData
	 * @Description: 菜单树的数据
	 * @param id
	 * @return
	 * @return: List<Menu>
	 */
	@RequestMapping(value = "/treeData", method = RequestMethod.POST)
	@ResponseBody
	public List<Menu> treeData() {
		return menuService.getAllMenus();
	}

	/**
	 * 
	 * @Title: middle
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/middle", method = RequestMethod.GET)
	public String middle() {
		return "/admin/menu/middle";
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加页面
	 * @param parent_id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long parent_id, Model model) {
		Menu parentMenu = null;
		if (parent_id != null) {
			parentMenu = menuService.queryById(parent_id);
		} else {
			parentMenu = Menu.genRoot();
		}
		model.addAttribute("parentMenu", parentMenu);
		return "/admin/menu/add";
	}

	/**
	 * 
	 * @Title: checkMenuNo
	 * @Description: 查看菜单是否是唯一的
	 * @param menu
	 * @param oldValue
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/checkMenuNo", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkMenuNo(Menu menu, String oldValue) {
		String newValue = menu.getMenuNo();
		if (menuService.isUnique("menuNo", oldValue, newValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: loadLeftMenu
	 * @Description: 获取左边的菜单
	 * @param menu
	 * @param oldValue
	 * @return
	 * @return: JSONArray
	 */
	@RequestMapping(value = "/loadLeftMenu", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray loadLeftMenu() {
		User currUser = getCurrentUser();
		if (currUser == null) {
			return null;
		}
		List<Menu> menuList = null;
		if (currUser.isSystemManager()) {
			// 如果是超级管理员就查询所有的菜单
			menuList = menuService.getAllMenus();

		} else {
			// 如果不是超级管理员就查询角色相对应的菜单
			menuList = menuService.getMenus(currUser.getRoleId());
		}

		if (menuList == null || menuList.size() == 0) {
			return null;
		}
		JSONArray jArray = new JSONArray();
		for (int i = 0; i < menuList.size(); i++) {
			jArray.add(menuService.getJson(menuList.get(i)));
		}
		return jArray;
	}

	/**
	 * 
	 * @Title: updateMenuPriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @param response
	 * @return
	 * @return: String
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "ids", message = "ID非法!"), @RequiredFieldValidator(fieldName = "priority", message = "排序非法!") })
	@RequestMapping(value = "/updateMenuPriority", method = RequestMethod.POST)
	public String updateMenuPriority(Long[] ids, Long[] priority, HttpServletResponse response) {
		if (ids.length != priority.length) {
			addActionError("ids长度与排序的长度不一致!");
			return ERROR_PAGE;
		}
		menuService.updateMenuPriority(ids, priority);
		return ResponseUtils.renderJSON(successMessage, response);
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加菜单
	 * @param menu
	 * @param response
	 * @param request
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Menu menu, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> filterParams = RequestUtils.getQueryParams(request);
		for (String str : filterParams.keySet()) {
			System.out.println(str + "," + filterParams.get(str));
		}
		if (MenuType.MENUTYPE_LINE.equals(menu.getType())) {
			// 分隔线
			menu.setName("-- 分隔线 --");
			menu.setUpMenuId(null);
			menuService.save(menu);
		} else if (MenuType.MENUTYPE_PARENT.equals(menu.getType())) {
			// 有子菜单
			menu.setUpMenuId(null);
			menuService.save(menu);
		} else if (MenuType.MENUTYPE_NODE.equals(menu.getType())) {
			Operate operate = new Operate();
			operate.setIsAuthorizationType(false);
			operate.setName("菜单所属动作");
			operate.setHtml(RequestUtils.getQueryParam(request, "operate.html"));
			operate.setPattern(RequestUtils.getQueryParam(request, "operate.pattern"));
			operate.setUrl(RequestUtils.getQueryParam(request, "operate.url"));
			operate.setIsAuthorizationType(false);
			operate.setIsDiaplayInToolBar(true);
			menu = menuService.saveMenuTypeNodeMenu(menu, operate);
		}
		Menu e_menu = menuService.get("menuNo", menu.getMenuNo());
		return ResponseUtils.renderJSON(menuService.getJson(e_menu), response);
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param id
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable Long id, Model model) {
		Menu menu = menuService.queryById(id);
		model.addAttribute("menu", menu);

		// 这边修改的时候要把菜单对应的动作也传到页面上去
		List<Operate> operates = operateService.getOperatesWidthCondition(id, false);
		if (operates != null && operates.size() > 0) {
			model.addAttribute("operate", operates.get(0));
		}
		return "/admin/menu/edit";
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param menu
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Menu menu, HttpServletResponse response) {
		// if (MenuType.MENUTYPE_NODE.equals(menu.getType()) &&
		// menu.getOperate() == null) {
		// addActionError("节点菜单的动作不能为空!");
		// return ResponseUtils.renderJSON(getErrors(), response);
		// }
		// if(MenuType.MENUTYPE_NODE.equals(menu.getType())){
		// menu.getOperate().setMenu(menu);
		// menu.getOperate().setType(2);
		// }
		//TODO修改还没完善
		menuService.updateSelective(menu);
		return ResponseUtils.renderJSON(menu, response);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param id
	 * @param response
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Long id, HttpServletResponse response) {
		Menu menu = menuService.queryById(id);
		menuService.remove(menu);
		return ResponseUtils.renderJSON(menu, response);
	}

	/**
	 * 
	 * @Title: getIcons
	 * @Description: 获取操作的小图标
	 * @return
	 * @return: JSONArray
	 */
	@RequestMapping(value = "/getIcons", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getIcons() {
		String real_path =  ApplicationContext.getAppRealPath(ICON_PATH);
		File dir = new File(real_path);
		List<FileWrap> fileWrapList = new FileWrap(dir).getChild();
		JSONArray arrayJson = new JSONArray();
		if (fileWrapList != null && fileWrapList.size() > 0) {
			for (int i = 0, len = fileWrapList.size(); i < len; i++) {
				FileWrap fileWrap = fileWrapList.get(i);
				arrayJson.add(fileWrap.getFile().getPath());
			}
		}

		// 增加配置文件中的路径图标
		Properties properties = PropertiesHelper.getPropertiesByPrefix("config", "silkicons_");
		if (properties != null && properties.size() > 0) {
			for (Object key : properties.keySet()) {
				String value = (String) properties.get(key);
				arrayJson.add(ApplicationContext.getAppRealPath(value));
			}
		}
		return arrayJson;
	}
}
