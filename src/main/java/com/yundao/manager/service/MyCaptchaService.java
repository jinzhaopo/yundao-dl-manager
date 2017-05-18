package com.yundao.manager.service;

import java.awt.image.BufferedImage;

/**
 * 
 * @ClassName: MyCaptchaService
 * @Description: 验证码service
 * @author: zhaopo
 * @date: 2016年11月4日 上午11:02:46
 */
public interface MyCaptchaService {

	/**
	 * 
	 * @Title: buildImage
	 * @Description: 创建验证码
	 * @param captchaId
	 * @return
	 * @return: BufferedImage
	 */
	public abstract BufferedImage buildImage(String captchaId);

	/**
	 * 
	 * @Title: isValid
	 * @Description: 验证验证码
	 * @param captchaId
	 * @param captcha
	 * @return
	 * @return: boolean
	 */
	public abstract boolean isValid(String captchaId, String captcha);
}
