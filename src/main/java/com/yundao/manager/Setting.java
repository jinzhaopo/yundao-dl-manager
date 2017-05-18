package com.yundao.manager;

import java.io.Serializable;

/**
 * 
 * @ClassName: Setting
 * @Description: 系统配置类
 * @author: zhaopo
 * @date: 2016年11月4日 上午9:07:41
 */
public class Setting implements Serializable {

	private static final long serialVersionUID = 5539786245179492300L;
	public static final String CACHE_NAME = "setting";
	public static final Integer CACHE_KEY = 0;

	/** 网站名称 **/
	private String name;

	/** 系统cookie识别码 **/
	private String cookieKey;

	/** Cookie路径 **/
	private String cookiePath;

	/** Cookie作用域 **/
	private String cookieDomain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

}
