<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加角色信息</title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
	$(function(){
		//$("#privTreeContainer").height($(window).height() - $("#mainContainer").height()-15);
		//$("#privTree").height($(window).height() - $("#mainContainer").height()-15);
		var mainform = $("#mainform");  
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "role_list");
		});
	});
	function doSubmit(){
		LG.submitForm(null, function(data){
			if(data.status == "success"){
				LG.showSuccess(data.message);
				$("form").clearForm();
			}else{
				LG.showError(data.message);
			}
		}, null);
	}
</script>
</head>
<#if !role??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<body style="overflow:hidden;padding:10px;">
	<form action="<#if isAdd??>save.jhtml<#else>update.jhtml</#if>" method="post" id="mainform" class="validate">
		<#if !isAdd??>
			<input type="hidden" name="id" value="${(role.id)!''}"/>
		</#if>
		<div id="mainContainer">
			<table class="table">
				<tr>
					<th>角色名称:</th>
					<td><input type="text" class="formText {required: true, remote: 'checkName.jhtml?oldValue=${(role.name)!}', messages: {remote: '角色名称已存在!'},maxlength: 20}" value="${(role.name)!''}" name="name"/></td>
				</tr>
				<tr>
					<th>角色标识:</th>
					<td>
						<input type="text" name="value" class="formText {required: true, minlength: 6,maxlength: 20, prefix: 'ROLE_', remote: 'checkValue.jhtml?oldValue=${(role.value)!}', messages: {remote: '角色标识已存在!'}}" value="${(role.value)!'ROLE_'}" title="角色标识长度不能小于6,且必须以ROLE_开头" />
					</td>
				</tr>
				<tr>
					<th>描述:</th>
					<td><input type="text" name="description" class="formText {maxlength:127}" value="${(role.description)!}" />
					</td>
				</tr>										
				<#if (role.isSystem)!false>
				<tr>
					<th>&nbsp;</th>
					<td><span class="warnInfo"><span class="icon">&nbsp;</span>系统提示：</b>系统内置角色不允许修改!</span>
					</td>
				</tr>
				</#if>
			</table>
		</div>
		<#--
		<div class="buttonArea">
			<input type="button" class="formButton" value="确  定" hidefocus="true" onclick="doSubmit();" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
		<div id="privTreeContainer">
			<iframe id="privTree" name="privTree" frameborder="0" src="${base}/role/tree.jhtml<#if isEdit??>?isEdit=true&id=${(role.id)!''}</#if>" scrolling="auto" 
					width="100%" height="100%" marginheight="0" marginwidth="0"></iframe>
		</div>
		-->
	</form>
</body>   
</html>