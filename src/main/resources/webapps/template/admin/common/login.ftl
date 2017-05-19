<!DOCTYPE html>
<html>
	<head>
		[#include "/admin/include/head.htm"]
		<link href="${base}/resource/css/login.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/resource/js/jsbn.js"></script>
		<script type="text/javascript" src="${base}/resource/js/prng4.js"></script>
		<script type="text/javascript" src="${base}/resource/js/rng.js"></script>
		<script type="text/javascript" src="${base}/resource/js/rsa.js"></script>
		<script type="text/javascript" src="${base}/resource/js/base64.js"></script>
		<script type="text/javascript" charset="utf-8">
			// 登录页面若在框架内，则跳出框架
			//if (self != top) {
			//	top.location = self.location;
			//};
			$().ready(function() {

				var $username = $("#username");
				var $password = $("#password");
				var $captcha = $("#captcha");
				var $captchaImage = $("#captchaImage");
				var $isSaveUsername = $("#isSaveUsername");

				// 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
				if ($.cookie("adminUsername") != null) {
					$isSaveUsername.attr("checked", true);
					$username.val($.cookie("adminUsername"));
					$password.focus();
				} else {
					$isSaveUsername.attr("checked", false);
					$username.focus();
				}

				// 提交表单验证,记住登录用户名
				$("#submitBt").click(function() {
					if ($username.val() == "") {
						LG.showError("${message('admin.login.usernameRequired')}");
						return false;
					}
					if ($password.val() == "") {
						LG.showError("${message('admin.login.passwordRequired')}");
						return false;
					}
					if ($captcha.val() == "") {
						LG.showError("${message('admin.login.captchaRequired')}");
						return false;
					}

					if ($isSaveUsername.attr("checked") == true) {
						$.cookie("adminUsername", $username.val(), {
							expires : 30
						});
					} else {
						$.cookie("adminUsername", null);
					}
					$.ajax({
						url : "${base}/admin/common/publicKey.jhtml",
						type : "GET",
						dataType : "json",
						cache : false,
						beforeSend : function() {
							$("#submitBt").prop("disabled", true);
						},
						success : function(data) {
							var rsaKey = new RSAKey();
							rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
							var enPassword = hex2b64(rsaKey.encrypt($password.val()));
							$.ajax({
								url : $("#loginForm").attr("action"),
								type : "POST",
								data : {
									username : $username.val(),
									enPassword : enPassword,
									captchaId : "${captchaId}",
									captcha : $captcha.val()
								},
								dataType : "json",
								cache : false,
								success : function(message) {
									if (message.type == "WARN") {
										LG.showError(message.content, function() {
											$("#submitBt").prop("disabled", false);
											$captchaImage.attr("src", "${base}/admin/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
										});
									} else {
										if ($isSaveUsername.prop("checked")) {
											Cookie.set("adminUsername", $username.val(), 7 * 24 * 60 * 60, "/");
										} else {
											Cookie.remove("adminUsername");
										}
										$("#submitBt").prop("disabled", false);
										location.href = "${base}/admin/index.jhtml";
									}
								},
								error : function(message) {
									$("#submitBt").prop("disabled", false);
									$captcha.val("");
									$captchaImage.attr("src", "${base}/admin/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
								}
							});
						}
					});
					return false;
				});

				// 刷新验证码
				$captchaImage.click(function() {
					$captchaImage.attr("src", "${base}/admin/common/captcha.jhtml?captchaId=${captchaId}&timestamp=" + (new Date()).valueOf());
				});
			});

		</script>
	</head>
	<body class="login"  style=" background-image: url(${base}/resource/images/bgimage1.jpg)">
		<div class="body">
			<div class="loginBox">
				<div class="boxTop"></div>
				<div class="boxMiddle">
					<form id="loginForm" class="form" action="${base}/admin/loginVerify.jhtml" method="post">
						<input type="hidden" name="captchaId" value="${captchaId}" />
						<div class="loginLogo">
							<img src="${base}/resource/images/login_logo1.jpg" />
						</div>
						<table class="loginTable">
							<tr>
								<th>${message("admin.login.username")}:</th>
								<td>
								<div class="formText">
									<input type="text" id="username" name="j_username" />
								</div></td>
							</tr>
							<tr>
								<th>${message("admin.login.password")}:</th>
								<td>
								<div class="formText">
									<input type="password" id="password" name="j_password" />
								</div></td>
							</tr>
							<tr>
								<th>${message("admin.captcha.name")}:</th>
								<td>
								<div class="formTextS">
									<input type="text" id="captcha" name="j_captcha" />
								</div>
								<div class="captchaImage">
									<img id="captchaImage" src="${base}/admin/common/captcha.jhtml?captchaId=${captchaId}" alt="换一张" />
								</div></td>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<td>
								<input type="checkbox" id="isSaveUsername" />
								<label for="isSaveUsername">&nbsp;${message("admin.login.rememberUsername")}</label></td>
							</tr>
							<tr>
								<th>&nbsp;</th>
								<td>
								<input type="button" class="homeButton ignoreForm" value="" onclick="window.open('${base}/')" hidefocus="true" />
								<input type="button" class="submitButton ignoreForm" value="登 陆" hidefocus="true"  id="submitBt"/>
								<input type="button" class="helpButton ignoreForm" value="帮 助" onclick="window.open('#')" hidefocus="true" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="boxBottom"></div>
			</div>
			<div class="blank"></div>
		</div>
	</body>
</html>