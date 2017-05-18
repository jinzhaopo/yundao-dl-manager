package com.yundao.manager.service;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: RSAService
 * @Description: 前端加密
 * @author: zhaopo
 * @date: 2016年11月4日 下午2:03:09
 */
public interface RSAService {

	public abstract RSAPublicKey generateKey(HttpServletRequest request);

	public abstract void removePrivateKey(HttpServletRequest request);

	public abstract String decryptParameter(String s, HttpServletRequest request);

	public abstract String decryptParameter(String s);
}
