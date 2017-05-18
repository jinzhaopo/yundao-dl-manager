// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package framework.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

/**
 * spring 工具类
 * 
 * @author jinzhaopo
 * @date 2016年7月8日上午9:04:58
 * @version 3.0.0
 */
@Component
public final class SpringUtils implements DisposableBean, ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private SpringUtils() {
	}

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void destroy() {
		applicationContext = null;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}

	public static Object getBean(String name, Class type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}

	/**
	 * 
	 * @Title: getMessage
	 * @Description: 获取国际化配置的值（前提必须要先配置国际化）
	 * @param code
	 * @param args
	 * @return
	 * @return: String
	 */
	public static String getMessage(String code, Object... args) {
		LocaleResolver localeresolver = (LocaleResolver) getBean("localeResolver", LocaleResolver.class);
		java.util.Locale locale = localeresolver.resolveLocale(null);
		return applicationContext.getMessage(code, args, locale);
	}
}
