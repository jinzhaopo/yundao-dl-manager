package com.yundao.manager.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Table;

import framework.entity.PriorityEntity;

/**
 * 
 * @ClassName: Operate
 * @Description: 操作表
 * @author: zhaopo
 * @date: 2016年11月3日 上午9:41:41
 */
@Table(name = "s_rbac_operate")
public class Operate extends PriorityEntity {

	private static final long serialVersionUID = -3817521535097551737L;
	/**
	 * 操作名称 唯一
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 是否授权动作(true:授权，false：普通)
	 */
	@Column(name = "is_authorization_type")
	private Boolean isAuthorizationType;
	/**
	 * 按钮图标
	 */
	@Column(name = "btn_icon")
	private String btnIcon;
	/**
	 * url
	 */
	private String url;
	/**
	 * 匹配模式
	 */
	private String pattern;
	/**
	 * 
	 */
	private String target;
	/**
	 * 
	 */
	private String html;
	/**
	 * 按钮图标是否显示在工具栏上
	 */
	@Column(name = "is_diaplay_in_tool_bar")
	private Boolean isDiaplayInToolBar;

	/****** 关联关系 *******/
	/**
	 * 按钮所属菜单
	 */
	@Column(name = "menu_id")
	private Long menuId;

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

	public Boolean getIsAuthorizationType() {
		return isAuthorizationType;
	}

	public void setIsAuthorizationType(Boolean isAuthorizationType) {
		this.isAuthorizationType = isAuthorizationType;
	}

	public String getBtnIcon() {
		return btnIcon;
	}

	public void setBtnIcon(String btnIcon) {
		this.btnIcon = btnIcon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Boolean getIsDiaplayInToolBar() {
		return isDiaplayInToolBar;
	}

	public void setIsDiaplayInToolBar(Boolean isDiaplayInToolBar) {
		this.isDiaplayInToolBar = isDiaplayInToolBar;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	
}