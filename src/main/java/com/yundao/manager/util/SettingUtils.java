package com.yundao.manager.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.yundao.manager.Setting;

import framework.util.MyConvertUtils;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class SettingUtils {

	private static final CacheManager cacheManager = CacheManager.getInstance();
	public static final String CONFIG_FILE_NAME = "setting.xml";// 系统配置文件名称
	public static final String SYSTEM_CONFIG_CACHE_KEY = "systemConfig";// systemConfig缓存Key
	private static final BeanUtilsBean beanUtilsBean;

	/**
	 * 获取系统配置信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Setting get() {
		Ehcache ehcache = cacheManager.getEhcache(Setting.CACHE_NAME);
		Element element = ehcache.get(Setting.CACHE_KEY);
		Setting setting;
		if (element != null) {
			setting = (Setting) element.getObjectValue();
		} else {
			setting = new Setting();
			try {
				File file = new ClassPathResource("/setting.xml").getFile();
				Document document = (new SAXReader()).read(file);
				List<org.dom4j.Element> list = document.selectNodes("/settings/setting");
				for (Iterator<org.dom4j.Element> iterator = list.iterator(); iterator.hasNext();) {
					org.dom4j.Element element1 = iterator.next();
					String s = element1.attributeValue("name");
					String s1 = element1.attributeValue("value");
					try {
						beanUtilsBean.setProperty(setting, s, s1);
					} catch (IllegalAccessException illegalaccessexception) {
						illegalaccessexception.printStackTrace();
					} catch (InvocationTargetException invocationtargetexception) {
						invocationtargetexception.printStackTrace();
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			ehcache.put(new Element(Setting.CACHE_KEY, setting));
		}
		return setting;
	}

	@SuppressWarnings("unchecked")
	public static void set(Setting setting) {
		File file = null;
		Document document = null;
		FileOutputStream fileoutputstream = null;
		XMLWriter xmlwriter = null;
		try {
			file = new ClassPathResource("/setting.xml").getFile();
			document = (new SAXReader()).read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<org.dom4j.Element> list = document.selectNodes("/setting/setting");
		for (Iterator<org.dom4j.Element> iterator = list.iterator(); iterator.hasNext();) {
			org.dom4j.Element element = iterator.next();
			try {
				String s = element.attributeValue("name");
				String s1 = beanUtilsBean.getProperty(setting, s);
				Attribute attribute = element.attribute("value");
				attribute.setValue(s1);
			} catch (IllegalAccessException illegalaccessexception) {
				illegalaccessexception.printStackTrace();
			} catch (InvocationTargetException invocationtargetexception) {
				invocationtargetexception.printStackTrace();
			} catch (NoSuchMethodException nosuchmethodexception) {
				nosuchmethodexception.printStackTrace();
			}
		}

		try {
			OutputFormat outputformat = OutputFormat.createPrettyPrint();
			outputformat.setEncoding("UTF-8");
			outputformat.setIndent(true);
			outputformat.setIndent("\t");
			outputformat.setNewlines(true);
			fileoutputstream = new FileOutputStream(file);
			xmlwriter = new XMLWriter(fileoutputstream, outputformat);
			xmlwriter.write(document);
		} catch (Exception exception1) {
			exception1.printStackTrace();
		} finally {
			if (xmlwriter != null) {
				try {
					xmlwriter.close();
				} catch (IOException ioexception) {

				}
			}
			if (fileoutputstream != null) {
				try {
					fileoutputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Ehcache ehcache = cacheManager.getEhcache("setting");
		ehcache.put(new Element(Setting.CACHE_KEY, setting));
	}

	static {
		MyConvertUtils myConvertUtils = new MyConvertUtils();
		DateConverter dateconverter = new DateConverter();
		dateconverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		myConvertUtils.register(dateconverter, Date.class);
		beanUtilsBean = new BeanUtilsBean(myConvertUtils);
	}

}
