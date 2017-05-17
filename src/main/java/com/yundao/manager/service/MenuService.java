package com.yundao.manager.service;

import java.util.List;

import com.yundao.manager.bean.MenuTree;
import com.yundao.manager.entity.rbac.Menu;
import com.yundao.manager.entity.rbac.Operate;

import framework.service.BaseService;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: MenuService
 * @Description: 菜单
 * @author: zhaopo
 * @date: 2016年11月4日 下午3:46:03
 */
public interface MenuService extends BaseService<Menu> {
	/**
	 * 
	 * @Title: getRoot
	 * @Description: 获取系统根路径
	 * @return
	 * @return: Menu
	 */
	public Menu getRoot();

	/**
	 * 
	 * @Title: getTopList
	 * @Description: 查询子菜单(获取顶级菜单)
	 * @return
	 * @return: List<Menu>
	 */
	public List<Menu> getTopList();

	/**
	 * 
	 * @Title: getSubMenuList
	 * @Description: 获取子菜单
	 * @param id
	 * @return
	 * @return: List<Menu>
	 */
	public List<Menu> getSubMenuList(Long id);

	/**
	 * 
	 * @Title: getAllMenus
	 * @Description: 获取所有的菜单类型
	 * @return
	 * @return: List<Menu>
	 */
	public List<Menu> getAllMenus();

	/**
	 * 
	 * @Title: getRoleSetString
	 * @Description: 获取权限字符串（以分隔符间隔）
	 * @param menuId
	 * @return
	 * @return: String
	 */
	public String getRoleSetString(Long menuId);

	/**
	 * 
	 * @Title: getMenuTree
	 * @Description: 获取菜单树（一级和二级的菜单）
	 * @param roleId
	 * @return
	 * @return: List<MenuTree>
	 */
	public List<MenuTree> getMenuTree(Long[] menuIds);

	/**
	 * 
	 * @Title: getMenus
	 * @Description: 菜单角色获取所有的
	 * @param roleId
	 * @return
	 * @return: List<Menu>
	 */
	public List<Menu> getMenus(Long roleId);

	/**
	 * 
	 * @Title: getJson
	 * @Description: 将菜单树变成json
	 * @param menuTree
	 * @return
	 * @return: JSONObject
	 */
	public JSONObject getJson(Menu menuTree);

	/**
	 * 
	 * @Title: updateMenuPriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @return: void
	 */
	public void updateMenuPriority(Long[] ids, Long[] priority);

	/**
	 * 
	 * @Title: save_MENUTYPE_NODE_menu
	 * @Description: 保存无子菜单
	 * @param menu
	 * @param operate
	 * @return: void
	 */
	public Menu saveMenuTypeNodeMenu(Menu menu, Operate operate);

	/**
	 * 
	 * @Title: remove
	 * @Description: 删除
	 * @param id
	 * @return: void
	 */
	public void remove(Menu menu);

}
