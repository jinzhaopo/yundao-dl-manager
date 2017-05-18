package com.yundao.manager;

/**
 * 
 * @ClassName: Constant
 * @Description: 常量类
 * @author: zhaopo
 * @date: 2016年11月3日 下午3:42:12
 */
public class Constant {
	/**
	 * 错误页面
	 */
	public static final String ERROR_PAGE = "/admin/common/error";
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * HTTP POST请求
	 */
	public static final String POST = "POST";
	/**
	 * cookie中的JSESSIONID名称
	 */
	public static final String JSESSION_COOKIE = "JSESSIONID";
	/**
	 * 是在request里面传递的
	 */
	public static final String WEBERRORS = "WebErrors";

	/** 授权动作 **/
	public static final boolean OPERATE_TYPE_AUTHORITY = true;

	/** 普通动作 **/
	public static final boolean OPERATE_TYPE_NORMAL = false;

	/**
	 * 公共的错误页面
	 */
	public static final String WEBERRORS_URL_REQUEST = "/admin/common/error.jhtml";
	/**
	 * 当前登入的用户session
	 */
	public static final String CURRENT_ADMIN = "current_admin";
	/**
	 * 登入的请求
	 */
	public static final String LOGINURL = "/admin/login.jhtml";

	/**
	 * 树的编码分隔符
	 */
	public static final char TREE_SEPARATE = '.';

	public static final String APPLICATION_URL_ROLE = "urlRole";
	/**
	 * 分隔符 权限
	 */
	public static final String SEPARATOR = ",";

	public static final String ADMIN_TEMPLATE_FOLDER = "/admin/template/";

	/***
	 * 存入数据库 验证码分隔符
	 */
	public static final String VERIFY_CODE_KEY_SEPARATOR = "_";
	/** 动态密码过期时间间隔 **/
	public static final int DYNAMIC_PASSWORD_PERIOD = 30;

	public static final String SYS_UPDATE = "sys_update";

	public static final String DATE_PATTERNS[] = { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

}
