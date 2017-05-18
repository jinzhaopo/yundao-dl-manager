package framework.util;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 项目的上下文
 * 
 * @author jinzhaopo
 * @date 2016年8月26日上午9:58:35
 * @version 3.0.0
 */
@Component
public class ApplicationContext implements ServletContextAware {

	private static ServletContext servletContext;

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;

	}

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public static ServletContext getContext() {
		return servletContext;
	}

	/**
	 * 获取ip
	 * 
	 * @return
	 */
	public static String getRemoteIp() {
		HttpServletRequest request = ApplicationContext.getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static Object getSessionAttr(String key) {
		HttpSession session = getRequest().getSession(false);
		if (session == null) {
			return null;
		} else {
			return session.getAttribute(key);
		}
	}

	/**
	 * 
	 * @Title: getCookie
	 * @Description: 获取cookie对象
	 * @param name
	 * @return
	 * @return: Cookie
	 */
	public static Cookie getCookie(String name) {
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getSessionId
	 * @Description: 获取sessionid判断是否session存在
	 * @param isCreate
	 * @return
	 * @return: String
	 */
	public static String getSessionId(boolean isCreate) {
		HttpSession session = getRequest().getSession(isCreate);
		if (session == null) {
			return null;
		} else {
			return session.getId();
		}
	}

	/**
	 * 
	 * @Title: setSessionAttr
	 * @Description: 设置session中的key-value
	 * @param key
	 * @param value
	 * @return: void
	 */
	public static void setSessionAttr(String key, Object value) {
		HttpSession session = getRequest().getSession(false);
		if (session == null) {
			session = getRequest().getSession(true);
		}
		session.setAttribute(key, value);
	}

	/**
	 * 
	 * @Title: removeAttribute
	 * @Description: 删除session中的obj
	 * @param key
	 * @return: void
	 */
	public static void removeAttribute(String key) {
		HttpSession session = getRequest().getSession(false);
		session.removeAttribute(key);
	}

	/**
	 * 
	 * @Title: getAppRealPath
	 * @Description: 获取url
	 * @param path
	 * @return
	 * @return: String
	 */
	public static String getAppRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	/**
	 * 
	 * @Title: getContextPath
	 * @Description: 获取上下文路径
	 * @return
	 * @return: String
	 */
	public static String getContextPath() {
		return servletContext.getContextPath();
		// return servletContext.getContextPath("/");
	}

	/**
	 * 
	 * @Title: getRequest
	 * @Description: 获取request
	 * @return
	 * @return: HttpServletRequest
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

}
