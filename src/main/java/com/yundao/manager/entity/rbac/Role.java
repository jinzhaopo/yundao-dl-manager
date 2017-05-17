package com.yundao.manager.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Table;

import framework.entity.PriorityEntity;

/**
 * 
 * @ClassName: Role
 * @Description: 角色
 * @author: zhaopo
 * @date: 2016年11月3日 上午11:18:10
 */
@Table(name = "s_rbac_role")
public class Role extends PriorityEntity {

	private static final long serialVersionUID = -6614052029623997372L;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色标识
	 */
	private String value;
	/**
	 * 是否为系统内置角色
	 */
	@Column(name="is_system")
	private Boolean isSystem;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 操作手册
	 */
	@Column(name = "operation_book")
	private String operationBook;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getIsSystem() {
		if(isSystem == null){
			isSystem = false;
		}
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		if(isSystem == null){
			isSystem = false;
		}
		this.isSystem = isSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperationBook() {
		return operationBook;
	}

	public void setOperationBook(String operationBook) {
		this.operationBook = operationBook;
	}

}