package com.yundao.manager.service.impl;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.octo.captcha.service.CaptchaService;
import com.yundao.manager.service.MyCaptchaService;

/**
 * 
 * @ClassName: CaptchaServiceImpl
 * @Description: 验证码
 * @author: zhaopo
 * @date: 2016年11月4日 上午11:03:55
 */
@Repository
public class CaptchaServiceImpl implements MyCaptchaService {

	@Resource
	private CaptchaService imageCaptchaService;

	/**
	 * 
	 * @Title: buildImage
	 * @Description: 创建验证码图片
	 * @param captchaId
	 * @return
	 * @see com.yundao.tour.service.MyCaptchaService#buildImage(java.lang.String)
	 */
	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	/**
	 * 
	 * @Title: isValid
	 * @Description: 验证验证码
	 * @param captchaId
	 * @param captcha
	 * @return
	 * @see com.yundao.tour.service.MyCaptchaService#isValid(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isValid(String captchaId, String captcha) {
		if (!StringUtils.isNotEmpty(captchaId) || !StringUtils.isNotEmpty(captcha)) {
			return false;
		}
		try {
			return imageCaptchaService.validateResponseForID(captchaId, captcha.toUpperCase()).booleanValue();
		} catch (Exception e) {
			return false;
		}
	}
}
