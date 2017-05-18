package com.yundao.manager.controller;

import java.security.interfaces.RSAPublicKey;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gaiya.ceo.service.MyCaptchaService;
import com.gaiya.ceo.service.RSAService;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: CommonController
 * @Description: 公共controller
 * @author: zhaopo
 * @date: 2016年11月4日 上午10:39:45
 */
@Controller
@RequestMapping("/admin/common")
public class CommonController extends BaseController {

	@Autowired
	private MyCaptchaService myCaptchaService;
	@Autowired
	private RSAService rSAService;

	/**
	 * 
	 * @Title: captcha
	 * @Description: 获取验证码
	 * @param model
	 * @param captchaId
	 * @param request
	 * @param response
	 * @return: void
	 */
	@RequestMapping("/captcha")
	public void captcha(Model model, String captchaId, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("captchaId", captchaId);
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		ServletOutputStream servletoutputstream = null;
		try {
			servletoutputstream = response.getOutputStream();
			java.awt.image.BufferedImage bufferedimage = myCaptchaService.buildImage(captchaId);
			ImageIO.write(bufferedimage, "jpg", servletoutputstream);
			servletoutputstream.flush();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletoutputstream);
		}
	}

	/**
	 * 
	 * @Title: error
	 * @Description: 公共错误页面
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		RequestAttributes requestattributes = RequestContextHolder.currentRequestAttributes();
		// model.addAttribute("errors", request.getAttribute("errors"));
		requestattributes.setAttribute("errors", request.getAttribute("errors"), 0);
		return "/admin/common/error";
	}

	/**
	 * 
	 * @Title: publicKey
	 * @Description: 前端加密
	 * @param request
	 * @return
	 * @return: JSONObject
	 */
	@RequestMapping("/publicKey")
	@ResponseBody
	public JSONObject publicKey(HttpServletRequest request) {
		RSAPublicKey rsapublickey = rSAService.generateKey(request);
		JSONObject obj = new JSONObject();
		obj.put("modulus", Base64.encodeBase64String(rsapublickey.getModulus().toByteArray()));
		obj.put("exponent", Base64.encodeBase64String(rsapublickey.getPublicExponent().toByteArray()));
		return obj;
	}
}
