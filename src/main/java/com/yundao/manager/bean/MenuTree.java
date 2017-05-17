package com.yundao.manager.bean;

import java.util.List;

import com.yundao.manager.entity.rbac.Menu;

/**
 * 
 * @ClassName: MenuTree
 * @Description: left 的菜单数据
 * @author: zhaopo
 * @date: 2016年11月14日 上午10:26:33
 */
public class MenuTree extends Menu {

	private List<Menu> subMenu;

	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	

}
