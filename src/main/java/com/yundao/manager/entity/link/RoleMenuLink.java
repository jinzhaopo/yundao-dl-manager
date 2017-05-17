package com.yundao.manager.entity.link;

import javax.persistence.Column;
import javax.persistence.Table;

import framework.entity.BaseEntity;

/**
 * 
 * @ClassName: RoleMenuLink
 * @Description: 角色和菜单的中间类
 * @author: zhaopo
 * @date: 2016年11月3日 下午1:44:42
 */
@Table(name = "s_rbac_role_menu_link")
public class RoleMenuLink extends BaseEntity {
	private static final long serialVersionUID = 7733208177427322826L;
	/**
	 * 角色id
	 */
	@Column(name = "role_id")
	private Long roleId;
	/**
	 * 菜单id
	 */
	@Column(name = "menu_id")
	private Long menuId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
