<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		[#include "/admin/include/head.htm"]
		<script type="text/javascript">
			/**
			 *	审核通过
			 **/
			function pass() {
				submitForm("yes")
			}
			/****审核不通过***/
			function noPass() {
				submitForm("no");
			}

			function submitForm(type) {
				$("#type").val(type);
				//ajax 请求发送验证码
				$.ajax({
					url: base + "/admin/coy/auditEdit.jhtml",
					data: $("#mainform").serialize(),
					dataType: "json",
					async: false,
					type: 'post',
					success: function(data) {
						//显示内容 并且关闭
						$.ligerDialog.confirm(data.content, function(confirm) {
							if(confirm)
								f_cancel();
							LG.reloadParent("coyAuditNo");
						});
					}
				});
			}
		</script>
	</head>

	<body>
		<div style="body-box">
			<form id="mainform" class="validate" action="${base}/admin/coy/auditEdit.jhtml" method="post">
				<input type="hidden" name="id" value="${coy.id}" />
				<input type="hidden" name="type" value="" id="type" />
				<table class="inputTable2">
					<tr>
						<th>手机：</th>
						<td>
							${coy.phone}
						</td>

					</tr>
					<tr>
						<th>公司名称：</th>
						<td>
							${coy.companyName}
						</td>

					</tr>
					<tr>
						<th>法人身份证：</th>
						<td>
							<img src="${base}${coy.legalIdCardPath}"/>
							
						</td>

					</tr>
					<tr>
						<th>营业执照：</th>
						<td>
						<img src=" ${base}${coy.businessLicensePath}"/>

						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" class="disabled" value="通过" onclick="pass();" />
							<input type="button" class="disabled" value="不通过" onclick="noPass()" />
						</td>
					</tr>
			</form>
		</div>
	</body>

</html>