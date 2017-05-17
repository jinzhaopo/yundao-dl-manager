package com.yundao.manager.entity.enumModel;
/**
 * 
 * @ClassName: MenuType
 * @Description: 菜单
 * @author: zhaopo
 * @date: 2016年11月3日 上午9:35:46
 */
public enum MenuType {
	MENUTYPE_PARENT("有子菜单"), MENUTYPE_NODE("无子菜单"), MENUTYPE_LINE("分割线");

	private String name;

	private MenuType(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}
}
