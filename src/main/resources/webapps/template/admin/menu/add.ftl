<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		[#include "/admin/include/head.htm"]
		<script type="text/javascript" src="${base}/resource/js/menu.js"></script>
		<script type="text/javascript" src="${base}/resource/js/iconselector.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#oIcon").click(function() {
					currentComboBox = this;
					f_openIconsWin();
				});
			});

			function doSubmit() {
				LG.submitForm($("#mainform"), function(data) {
					window.parent.leftFrame.addNode(data, false);
					LG.showSuccess("添加菜单成功!");
					$("form").clearForm();
				},null,null);
			}
		</script>
	</head>
	<body>
		<div class="body-box">
			<form action="add.jhtml" method="post" class="validate" id="mainform">
				<table class="inputTable2">
					<tr>
						<th>父菜单：</th>
						<td> ${(parentMenu.name)!''}
						[#if parentMenu.id??]
						<input type="hidden" name="upMenuId" value="${(parentMenu.id)!''}"/>
						[/#if] </td>
					</tr>
					<tr id="_menuName">
						<th>菜单名：</th>
						<td>
						<input name="name" class="formText {required: true}"/>
						</td>
					</tr>
					<tr id="_menuNo">
						<th>菜单编号：</th>
						<td>
						<input name="menuNo" class="formText {required: true, username:true, maxlength:200, remote:'${base}/admin/menu/checkMenuNo.jhtml', messages: {remote: '菜单编号!'}}"/>
						</td>
					</tr>
					<tr>
						<th>菜单类型：</th>
						<td>[@u.select_map headerKey="" headerValue="顶级分类" onchange="showHideActionRow(this);"
						headerButtom="false" list=ViewStaticModels.getMenuTypes() name="type" id="menuType"/] </td>
					</tr>
					<tr id="menuUrl" style="display:none">
						<th>操作URL：</th>
						<td>
						<input name="operate.url" class="formText" style="width:300px;"/>
						</td>
					</tr>
					<tr id="menuPattern" style="display:none">
						<th>URL规则：</th>
						<td>
						<input name="operate.pattern" class="formText" style="width:300px;"/>
						</td>
					</tr>
					<tr id="menuHtml" style="display:none">
						<th>HTML：</th>
						<td>						<textarea name="operate.html" class="text" style="width:350px;height:80px"></textarea></td>
					</tr>
					<tr>
						<th>菜单说明</th>
						<td>						<textarea name="description" class="text" style="width:300px;height:56px"></textarea></td>
					</tr>
				</table>

				<div class="buttonArea">
					<input type="button" class="formButton ok" value="确定" onclick="doSubmit()"/>
					<input type="button" class="formButton cancel" value="取消" onclick="location.href='detail.jhtml?id=${(parentMenu.id)!''}'"/>
				</div>
			</form>
		</div>
	</body>
</html>