package com.yundao.manager.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yundao.manager.entity.enumModel.MenuType;

import framework.entity.PriorityEntity;

/**
 * 
 * @ClassName: Menu
 * @Description: 系统模块表
 * @author: zhaopo
 * @date: 2016年11月3日 上午9:34:32
 */
@Table(name = "s_rbac_menu")
public class Menu extends PriorityEntity {

	private static final long serialVersionUID = 8607524172042357041L;
	/**
	 * 模块名称 唯一
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 菜单编号 唯一
	 */
	@Column(name = "menu_no")
	private String menuNo;
	/**
	 * 菜单类别(1:有子菜单，2:无子菜单， 3:分割线)
	 */

	private MenuType type;

	/****** 关联关系 ********/

	/**
	 * 上级模块
	 */
	@Column(name = "up_menu_id")
	private Long upMenuId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public MenuType getType() {
		return type;
	}

	public void setType(MenuType type) {
		this.type = type;
	}

	public Long getUpMenuId() {
		return upMenuId;
	}

	public void setUpMenuId(Long upMenuId) {
		this.upMenuId = upMenuId;
	}

	@Transient
	public static Menu genRoot() {
		Menu rootModule = new Menu();
		rootModule.setName("系统根路径");
		rootModule.setType(MenuType.MENUTYPE_PARENT);
		rootModule.setDescription("系统菜单的根对象");
		return rootModule;
	}

}