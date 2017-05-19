<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<#include "/admin/include/head.htm">
<script type="text/javascript" src="${base}/resource/js/menu.js"></script>
<script type="text/javascript" src="${base}/resource/js/iconselector.js"></script>
<script type="text/javascript">
	$(function() {
		$("#oIcon").click(function() {
			currentComboBox = this;
			f_openIconsWin();
		});
	});
</script>
</head>
	<#if !operate??> 
		<#assign isAdd = true /> 
	<#else> 
		<#assign isEdit = true/> 
	</#if>
<body>
	<div class="body-box">
		<form
			action="<#if isAdd??>${base}/admin/operate/add.jhtml<#else>${base}/admin/operate/update.jhtml</#if>"
			method="post" class="validate">
			<#if !isAdd??> <input type="hidden" name="id"
				value="${(operate.id)!''}" /> </#if>
			<table class="inputTable2">
				<tr>
					<th>所属菜单：</th>
					<td>${(menu.name)!''} <input type="hidden" name="menu_id"
						value="${(menu.id)!''}" />
					</td>
				</tr>
				<tr id="menuUrlRow">
					<th>操作名称：</th>
					<td><input name="name" class="formText" maxlength="100"
						value="${(operate.name)!''}" /></td>
				</tr>
				<tr id="menuNameRow">
					<th>操作URL：</th>
					<td><input name="url" class="formText" maxlength="100"
						value="${(operate.url)!''}" style="width: 300px;" /></td>
				</tr>
				<tr id="menuPattern">
					<th>URL规则：</th>
					<td><input name="pattern" class="formText"
						value="${(operate.pattern)!''}" style="width: 300px;" /></td>
				</tr>
				<tr id="menuHtml">
					<th>HTML：</th>
					<td><textarea name="html" class="text"
							style="width: 350px; height: 80px">${(operate.html)!''}</textarea>
					</td>
				</tr>
				<tr id="menuCode">
					<th>是否显示在表格工具栏上：</th>
					<td><@u.radio name="isDiaplayInToolBar" list={'true': '是',
						'false': '否'} value="${(operate.isDiaplayInToolBar?string('true',
						'false'))!'false'}"/></td>
				</tr>
				<tr id="menuCode">
					<th>模块图标：</th>
					<td><input name="btnIcon" class="formText" id="oIcon"
						value="${(operate.btnIcon)!''}" style="width: 300px;" /> <input
						type="button" value="新增"
						onclick="$('#oIcon').val('resource/images/icons/silkicons/add.png')" />
						<input type="button" value="修改"
						onclick="$('#oIcon').val('resource/images/icons/silkicons/application_edit.png')" />
						<input type="button" value="删除"
						onclick="$('#oIcon').val('resource/images/icons/silkicons/delete.png')" />
						<input type="button" value="查看"
						onclick="$('#oIcon').val('resource/images/icons/silkicons/application_view_detail.png')" />
						<input type="button" value="清空" onclick="$('#oIcon').val('')" /></td>
				</tr>
				<tr>
					<th>动作类型：</th>
					<td><@u.radio name="isAuthorizationType" value="${(operate.isAuthorizationType)!'false'}"
						list={'false':'普通动作', 'true':'授权动作'} /></td>
				</tr>
				<tr>
					<th>动作说明</th>
					<td><textarea name="description" class="formText"
							maxlength="300" style="width: 300px; height: 56px">${(operate.description)!''}</textarea></td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton ok" value="确定" /> <input
					type="button" class="formButton cancel" value="取消"
					onclick="window.history.back();" />
			</div>
		</form>
	</div>
</body>
</html>