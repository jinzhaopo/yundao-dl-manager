package com.yundao.manager.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.manager.Constant;
import com.yundao.manager.bean.MenuTree;
import com.yundao.manager.entity.enumModel.MenuType;
import com.yundao.manager.entity.link.RoleMenuLink;
import com.yundao.manager.entity.link.RoleOperateLink;
import com.yundao.manager.entity.rbac.Menu;
import com.yundao.manager.entity.rbac.Operate;
import com.yundao.manager.entity.rbac.Role;
import com.yundao.manager.mapper.MenuMapper;
import com.yundao.manager.service.MenuService;
import com.yundao.manager.service.OperateService;
import com.yundao.manager.service.RoleMenuLinkService;
import com.yundao.manager.service.RoleOperateLinkService;
import com.yundao.manager.service.RoleService;

import framework.config.SystemConfig;
import framework.page.SearchFilter;
import framework.page.SearchFilter.Operator;
import framework.service.impl.BaseServiceImpl;
import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName: MenuServiceImpl
 * @Description: 菜单service
 * @author: zhaopo
 * @date: 2016年11月4日 下午3:47:32
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMenuLinkService roleMenuLinkService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private OperateService operateService;

	@Autowired
	private RoleOperateLinkService roleOperateLinkService;

	@Autowired
	public void setMapper(MenuMapper mapper) {
		super.setMapper(mapper);
	}

	/**
	 * 
	 * @Title: getRoot
	 * @Description: 获取系统根路径
	 * @return
	 * @see com.yundao.tour.service.MenuService#getRoot()
	 */
	public Menu getRoot() {
		Menu rootModule = new Menu();
		rootModule.setName("系统根路径");
		rootModule.setType(MenuType.MENUTYPE_PARENT);
		rootModule.setDescription("系统菜单的根对象");
		return rootModule;
	}

	/**
	 * 
	 * @Title: getTopList
	 * @Description: 获取顶级菜单
	 * @return
	 * @see com.yundao.tour.service.MenuService#getTopList()
	 */
	public List<Menu> getTopList() {
		// 通用Example查询
		Example example = new Example(Menu.class);
		example.createCriteria().andIsNull("upMenuId");
		return selectByExample(example);
	}

	/**
	 * 
	 * @Title: getSubMenuList
	 * @Description: 获取子菜单
	 * @param id
	 * @return
	 * @see com.yundao.tour.service.MenuService#getSubMenuList(java.lang.Long)
	 */
	public List<Menu> getSubMenuList(Long id) {
		Example example = new Example(Menu.class);
		example.createCriteria().andEqualTo("upMenuId", id);
		example.orderBy("priority").asc();
		return selectByExample(example);
	}

	/**
	 * 
	 * @Title: getAllMenus
	 * @Description: 获取所有的菜单
	 * @return
	 * @see com.yundao.tour.service.MenuService#getAllMenus()
	 */
	public List<Menu> getAllMenus() {

		Example example = new Example(Menu.class);
		example.orderBy("priority").asc();
		return selectByExample(example);
	}

	/**
	 * 
	 * @Title: getRoleSetString
	 * @Description: 获取权限字符串（以分隔符间隔）
	 * @param menuId
	 * @return
	 * @see com.yundao.tour.service.MenuService#getRoleSetString(java.lang.Long)
	 */
	public String getRoleSetString(Long menuId) {
		StringBuffer stringBuffer = new StringBuffer();

		List<RoleMenuLink> list = roleMenuLinkService.getList("menuId", menuId);
		Set<Long> idSet = new LinkedHashSet<Long>();
		for (RoleMenuLink rml : list) {
			idSet.add(rml.getRoleId());
		}

		List<Role> roleSet = roleService.queryByIds(new ArrayList<Long>(idSet));
		if (roleSet != null) {
			for (Role role : roleSet) {
				stringBuffer.append(Constant.SEPARATOR + role.getValue());
			}
		}

		// 增加系统管理员的权限
		String system_manager_role = systemConfig.getManagerRole();
		stringBuffer.append(Constant.SEPARATOR + system_manager_role);

		if (stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(0);
		}
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @Title: getMenuTree
	 * @Description: 获取left的菜单树数据
	 * @param roleId
	 * @return
	 * @see com.yundao.tour.service.MenuService#getMenuTree(java.lang.Long)
	 */
	public List<MenuTree> getMenuTree(Long[] menuIds) {
		return menuMapper.getMenuTree(menuIds);
	}

	/**
	 * 
	 * @Title: getJson
	 * @Description: 将菜单数据变成json
	 * @param menuTree
	 * @return
	 * @see com.yundao.tour.service.MenuService#getJson(com.yundao.tour.bean.MenuTree)
	 */
	public JSONObject getJson(Menu menuTree) {
		JSONObject obj = new JSONObject();
		obj.put("idKey", "m"+menuTree.getId());
		obj.put("pIdKey", "m"+menuTree.getUpMenuId());
		obj.put("id", menuTree.getId());
		obj.put("name", menuTree.getName());
		obj.put("type", "menu");
		obj.put("menuNo", menuTree.getMenuNo());
		if (menuTree.getId() == null) {
			obj.put("open", true);
		} else {
			obj.put("parentId", menuTree.getUpMenuId());
			// 如果是最终菜单，放入操作图标
			if (MenuType.MENUTYPE_NODE.equals(menuTree.getType())) {
				Operate operate = operateService.getOperatesWidthCondition(menuTree.getId(), false).get(0);
				obj.put("operateIcon", operate.getBtnIcon());
				obj.put("html", operate.getHtml());
				obj.put("mUrl", operate.getUrl());
			}
		}
		return obj;
	}

	/**
	 * 
	 * @Title: getMenus
	 * @Description: 工具角色获取所有的菜单
	 * @param roleId
	 * @return
	 * @see com.yundao.tour.service.MenuService#getMenus(java.lang.Long)
	 */
	public List<Menu> getMenus(Long roleId) {
		List<RoleMenuLink> list = roleMenuLinkService.getList("roleId", roleId);
		List<Long> idsList = new ArrayList<Long>();
		for (RoleMenuLink rml : list) {
			idsList.add(rml.getMenuId());
		}
		return queryByIds(idsList);
	}

	/**
	 * 
	 * @Title: updateMenuPriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @see com.yundao.tour.service.MenuService#updateMenuPriority(java.lang.Long[],
	 *      java.lang.Integer[])
	 */
	public void updateMenuPriority(Long[] ids, Long[] priority) {
		for (int i = 0, len = ids.length; i < len; i++) {
			Menu menu = new Menu();
			menu.setId(ids[i]);
			menu.setPriority(priority[i]);

			updateSelective(menu);
		}
	}

	/**
	 * 
	 * @Title: save_MENUTYPE_NODE_menu
	 * @Description: 保存无子菜单
	 * @param menu
	 * @param operate
	 * @see com.yundao.tour.service.MenuService#save_MENUTYPE_NODE_menu(com.yundao.tour.model.rbac.Menu,
	 *      com.yundao.tour.model.rbac.Operate)
	 */
	public Menu saveMenuTypeNodeMenu(Menu menu, Operate operate) {
		// 因为menu_no是唯一的 所以可以通过这个区查找
		int i = 0;
		save(menu);
		Menu e_menu = get("menuNo", menu.getMenuNo());
		operate.setMenuId(e_menu.getId());
		operateService.save(operate);
		return e_menu;
	}

	/**
	 * 
	 * @Title: remove
	 * @Description: TODO
	 * @param id
	 * @see com.yundao.tour.service.MenuService#remove(java.lang.Long)
	 */
	public void remove(Menu menu) {
		int i = 0;
		//
		List<Menu> menus = getList("upMenuId", menu.getId());
		if (menus != null && menus.size() > 0) {
			remove(menu);
		}
		if (menu.getType() == MenuType.MENUTYPE_NODE) {
			// 若是无子菜单就有动作
			// 1.获取所有的动作
			List<Operate> list = operateService.getList("menuId", menu.getId());
			List<Long> ids = new ArrayList<Long>();
			if (list != null && list.size() > 0) {
				for (Operate operate : list) {
					ids.add(operate.getId());
				}
			}

			List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
			searchFilters.add(new SearchFilter("operateId", Operator.IN, ids));
			List<RoleOperateLink> rols = roleOperateLinkService.getList(searchFilters);

			if (rols != null && rols.size() > 0) {
				List<Object> rolIds = new ArrayList<Object>();
				for (RoleOperateLink rol : rols) {
					rolIds.add(rol.getId());
				}
				roleOperateLinkService.deleteByIds(RoleOperateLink.class, "id", rolIds);
			}

		}

		deleteById(menu.getId());
		// 删除角色菜单的中间表
		RoleMenuLink rml = new RoleMenuLink();
		rml.setMenuId(menu.getId());
		roleMenuLinkService.deleteByWhere(rml);
	}

}
