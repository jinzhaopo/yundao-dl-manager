<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<#include "/admin/include/head.htm">
<#if !area??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<script type="text/javascript">
</script>
</head>
<body style="margin:10px;">
	<form id="inputForm" class="validate" action="<#if isAdd??>save.jhtml<#else>update.jhtml</#if>" method="post">
		<#if isEdit??> <input type="hidden" name="id" value="${area.id}"/></#if>
		<#if parent??><input type="hidden" name="parentId" value="${parent.id}" /> </#if>
		<table class="table">
			<tr>
				<th>
					上级地区:
				</th>
				<td>
					<#if parent??>${(parent.name)!}<#else>一级地区</#if>
				</td>
			</tr>
			<tr>
				<th>地区名称:</th>
				<td>
					<input type="text" name="name" class="formText {required: true, minlength:2,maxlength:12,remote: 'checkName.jhtml?oldValue=${(area.name)!}&parentId=${(parent.id)!}', messages: {remote: '此地区名称已存在!'}}" value="${(area.name)!''}" />
				</td>
			</tr>
			<tr>
				<th>排序:</th>
				<td>
					<input type="text" name="orderBy" class="formText {digits:true}" value="${(area.orderBy)!'10'}"/>
				</td>
			</tr>
		</table>
		<div class="buttonArea">
			<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
		</div>
	</form>
</body>
</html>