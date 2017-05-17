package com.yundao.manager.entity.link;

import javax.persistence.Column;
import javax.persistence.Table;

import framework.entity.BaseEntity;

/**
 * 
 * @ClassName: RoleOperateLink
 * @Description: 角色操作关联表
 * @author: zhaopo
 * @date: 2016年11月3日 下午1:48:44
 */
@Table(name = "s_rbac_role_operate_link")
public class RoleOperateLink extends BaseEntity {

	private static final long serialVersionUID = 7795086183157827933L;
	/**
	 * 角色id
	 */
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 操作id
	 */
	@Column(name = "operate_id")
	private Long operateId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

}
