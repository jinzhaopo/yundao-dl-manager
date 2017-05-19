<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		[#include "/admin/include/head.htm"]
		<script type="text/javascript" src="${base}/resource/js/menu.js"></script>
		<script type="text/javascript">
			$(function() {
				showHideActionRow($("#menuType")[0]);
			});

			function doSubmit() {
				LG.submitForm(null, function(data) {
					window.parent.leftFrame.updateNode(data);
					LG.showSuccess("修改菜单成功!");
					$("form").clearForm();
				});
			}
		</script>
	</head>
	<body>
		<div class="body-box">
			<form action="../update.jhtml" method="post" class="validate">
				<input type="hidden" name="id" value="${(menu.id)!''}"/>
				<table class="inputTable2">
					<tr>
						<th>父菜单：</th>
						<td> 
						[#if menu.upMenuId??]
							[@rbac method="getMenu" param="{id:"+menu.upMenuId+"}"]
								[#if tag_bean??]
									${(tag_bean.name)!''}
								[/#if]
							[/@rbac ]
							<input type="hidden" name="upMenuId" value="${(menu.upMenuId)!''}"/>
						[#else]
							${(menu.genRoot().name)!''}
						[/#if] </td>
					</tr>
					<tr id="_menuName">
						<th>菜单名：</th>
						<td>
						<input type="text" name="name" class="formText {required: true}" value="${(menu.name)!''}"/>
						</td>
					</tr>
					<tr id="_menuNo">
						<th>菜单编号：</th>
						<td>
						<input name="menuNo" class="formText {username:true, maxlength:200, remote: '${base}/admin/menu/checkMenuNo.jhtml?oldValue=${(menu.menuNo)!''}', messages: {remote: '菜单编号!'}}"
						value="${(menu.menuNo)!''}"/>
						</td>
					</tr>
					<tr>
						<th>菜单类型：</th>
						<td>[@u.select_map headerKey="" headerValue="顶级分类" onchange="showHideActionRow(this);"
						headerButtom="false" list=ViewStaticModels.getMenuTypes() name="menu.type" id="menuType" disabled="true"/]
						<input type="hidden" name="type" value="${(menu.type)!''}"/>
						</td>
					</tr>
					[#if menu.type == 'MENUTYPE_NODE']
					<input type="hidden" name="operate.id" value="${(operate.id)!''}"/>
					<tr id="menuUrl" style="display:none">
						<th>操作URL：</th>
						<td>
						<input type="text" name="operate.url" class="formText" value="${(operate.url)!''}" style="width:300px;"/>
						</td>
					</tr>
					<tr id="menuPattern" style="display:none">
						<th>URL规则：</th>
						<td>
						<input type="text" name="operate.pattern" class="formText" maxlength="100" value="${(operate.pattern)!''}" style="width:300px;"/>
						</td>
					</tr>
					<tr id="menuHtml" style="display:none">
						<th>HTML：</th>
						<td>						<textarea name="operate.html" class="text" style="width:350px;height:80px">${(menu.operate.html)!''}</textarea></td>
					</tr>
					[/#if]
					<tr>
						<th>菜单说明</th>
						<td>						<textarea name="description" class="text" style="width:300px;height:56px">${(menu.description)!''}</textarea></td>
					</tr>
				</table>

				<div class="buttonArea">
					<input type="button" class="formButton ok" value="确定" onclick="doSubmit();"/>
					<input type="button" class="formButton cancel" value="取消" onclick="window.history.back();"/>
				</div>
			</form>
		</div>
	</body>
</html>