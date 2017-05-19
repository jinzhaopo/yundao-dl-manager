<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#include "/admin/include/head.htm">
		<#include "/admin/include/upload.ftl">
		<script type="text/javascript" src="${base}/resource/js/input.js"></script>
		<title>站点设置</title>
		<script type="text/javascript">
			$(function() {

				$("#sendMessage").click(function() {
					var testPhone = $("#testPhone").val();
					var testContent = $("#testContent").val();
					$.ajax({
						url : base + "/admin/setting/testMessage.jhtml",
						data : {
							"testPhone" : testPhone,
							"testContent" : testContent
						},
						dataType : "json",
						type:"post",
						async : false,
						beforeSend : function(data) {
						},
						success : function(data) {
							if (data) {
								alert("发送成功");
							}else{
								alert("发送失败");
							}
						}
					});
				});

				$("#tabcontainer").ligerTab();
				$(".browserButton").browser();
				//表单底部按钮
				LG.setFormDefaultBtn(f_cancel, function() {

					$("#validateForm").submit();
				});

				//显示错误信息
				var errorUl = "${errorUl}";
				if (errorUl.isNotEmpty()) {
					alert(errorUl);
				}
			});
		</script>
		<style type="text/css">
			#tabcontainer .l-tab-links ul {
				margin-left: 160px;
			}
			#tabcontainer {
				padding-top: 20px;
			}
			.l-tab-links {
				background: none;
			}
			.l-tab-links li {
				margin: 0 0 0 12px;
			}
		</style>
	</head>
	<body>
		<form action="update.jhtml" method="post" class="validate" id="validateForm" enctype="multipart/form-data">
			<!--这边是隐藏的内容-->
			<input type="hidden" name="name" value="${(setting.name)!''}" />
			<input type="hidden" name="cookie_key" value="${(setting.cookie_key)!''}" />

			<input type="hidden" name="cookiePath" value="${(setting.cookiePath)!''}" />

			<input type="hidden" name="cookieDomain" value="${(setting.cookieDomain)!''}" />

			<input type="hidden" name="expirationTime" value="${(setting.expirationTime)!''}" />

			<input type="hidden" name="checkInTime" value="${(setting.checkInTime)!''}" />

			<input type="hidden" name="licenceNumberVerify" value="${(setting.licenceNumberVerify)!''}" />

			<input type="hidden" name="priceOfAnnualTicket" value="${(setting.priceOfAnnualTicket)!''}" />

			<input type="hidden" name="yearTicketTimeLength" value="${(setting.yearTicketTimeLength)!''}" />

			<input type="hidden" name="updateTouristTrigger" value="${(setting.updateTouristTrigger)!''}" />

			<div class="validateErrorContainer" id="validateErrorContainer" style="display: none;">
				<div class="validateErrorTitle">
					<!--以下信息填写有误,请重新填写-->
				</div>
				<ul style="display: none;" id="errorUl"></ul>
			</div>
			<div id="tabcontainer" style="overflow:hidden;margin:3px;">
				<!-- 关于我们 begin -->
				<div title="基本参数">
					<table width="600" align="center" class="inputTable2" style="display:table;">
						<tr>
							<th>地区前缀：</th>
							<td>
							<input type="text" class="formText" name="licenceNumberVerify" value="${(setting.licenceNumberVerify)!''}"/>
							(多个关键词请用,分割) </td>
						</tr>
					</table>
				</div>
				<!-- 短信设置-->
				<div title="短信接口设置">
					<table width="600" align="center" class="inputTable2" style="display:table;">
						<tr>
							<th>用户名：</th>
							<td>
							<input type="text" class="formText" name="smsUserName" value="${(setting.smsUserName)!''}"/>
							</td>
						</tr>
						<tr>
							<th>密码：</th>
							<td>
							<input type="text" class="formText" name="smsPassword" value="${(setting.smsPassword)!''}"/>
							</td>
						</tr>
						<tr>
							<th>密钥：</th>
							<td>
							<input type="text" class="formText" name="smsApikey" value="${(setting.smsApikey)!''}"/>
							</td>
						</tr>

						<tr>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th>测试手机：</th>
							<td>
							<input type="text" class="formText" name="testPhone" id="testPhone"/>
							</td>
						</tr>
						<tr>
							<th>测试内容：</th>
							<td>
							<input type="text" class="formText" name="testContent" id="testContent"/>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
							<input type="button" class="formButton" id="sendMessage"  value="发送测试"/>
							<span id="messageSpan"></span></td>
						</tr>
					</table>
				</div>
				<!-- 技术支持
				<div title="技术支持">
				<table width="600" align="center" class="inputTable2" style="display:table;">
				<tr>
				<th>QQ：</th>
				<td>2433743296</td>
				</tr>
				<tr>
				<th>联系电话：</th>
				<td>86587300</td>
				</tr>
				<tr>
				<th></th>
				<td>欢迎咨询</td>
				</tr>
				</table>
				</div>-->

				<!-- 更新设置
				<div title="更新设置">
				<table width="600" align="center" class="inputTable2" style="display:table;">
				<tr>
				<th>系统更新时间：</th>
				<td>
				<input type="file" name="updateFile" id="updateFile"
				class="formText {required: true}" value="" />
				</td>
				</tr>
				</table>
				</div>-->
			</div>
		</form>
	</body>
</html>