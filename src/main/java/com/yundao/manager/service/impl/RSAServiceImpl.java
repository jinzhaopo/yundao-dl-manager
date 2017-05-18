package com.yundao.manager.service.impl;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yundao.manager.service.RSAService;

import framework.util.ApplicationContext;
import framework.util.RSAUtils;

/**
 * 
 * @ClassName: RSAServiceImpl
 * @Description: 前端加密
 * @author: zhaopo
 * @date: 2016年11月4日 下午2:03:50
 */
@Repository
public class RSAServiceImpl implements RSAService {

	private static final String privateKey = "privateKey";

	public RSAPublicKey generateKey(HttpServletRequest request) {
		Assert.notNull(request);
		KeyPair keypair = RSAUtils.generateKeyPair();
		RSAPublicKey rsapublickey = (RSAPublicKey) keypair.getPublic();
		RSAPrivateKey rsaprivatekey = (RSAPrivateKey) keypair.getPrivate();
		HttpSession httpsession = request.getSession();
		httpsession.setAttribute(privateKey, rsaprivatekey);
		return rsapublickey;
	}

	public void removePrivateKey(HttpServletRequest request) {
		Assert.notNull(request);
		HttpSession httpsession = request.getSession();
		httpsession.removeAttribute(privateKey);
	}

	public String decryptParameter(String name, HttpServletRequest request) {
		Assert.notNull(request);
		if (name != null) {
			HttpSession httpsession = request.getSession();
			RSAPrivateKey rsaprivatekey = (RSAPrivateKey) httpsession.getAttribute(privateKey);
			String s = request.getParameter(name);
			if (rsaprivatekey != null && StringUtils.isNotEmpty(s)) {
				return RSAUtils.decrypt(rsaprivatekey, s);
			}
		}
		return null;
	}

	public String decryptParameter(String name) {
		HttpServletRequest request = ApplicationContext.getRequest();
		Assert.notNull(request);
		if (name != null) {
			HttpSession httpsession = request.getSession();
			RSAPrivateKey rsaprivatekey = (RSAPrivateKey) httpsession.getAttribute(privateKey);
			if (rsaprivatekey != null && StringUtils.isNotEmpty(name)) {
				return RSAUtils.decrypt(rsaprivatekey, name);
			}
		}
		return null;
	}
}
