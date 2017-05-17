package com.yundao.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yundao.manager.bean.MenuTree;
import com.yundao.manager.entity.rbac.Menu;

import framework.mapper.MyMapper;

/**
 * 
 * @ClassName: MenuDao
 * @Description: 菜单
 * @author: zhaopo
 * @date: 2016年11月3日 下午2:11:24
 */
public interface MenuMapper extends MyMapper<Menu> {

	/**
	 * 
	 * @Title: getMenuTree
	 * @Description: 获取left菜单树的数据
	 * @param roleId
	 * @return
	 * @return: List<MenuTree>
	 */
	public List<MenuTree> getMenuTree(@Param("menuIds") Long[] menuIds);

	/**
	 * 
	 * @Title: getMenus
	 * @Description: 根据角色id获取资源
	 * @param roleId
	 * @return
	 * @return: List<Menu>
	 */
	public List<Menu> getMenus(@Param("roleId") Long roleId);
}
