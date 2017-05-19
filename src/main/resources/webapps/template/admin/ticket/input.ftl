<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
	$(function(){
		var mainform = $("#mainform");  
		//表单底部按钮 
    	LG.setFormDefaultBtn(f_cancel, function(){
			f_save(mainform, "ticket_list");
		});
	});
</script>
</head>
<#if !ticket??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<body scroll="no" style="padding:30px;overflow:hidden;">
<form action="<#if isAdd??>${base}/admin/ticket/add.jhtml<#else>${base}/admin/ticket/edit.jhtml</#if>" method="post" id="mainform" class="validate">
	<#if !isAdd??>
		<input type="hidden" name="id" value="${(ticket.id)!''}"/>
	</#if>
	<table class="table">
		<tr>
			<th>套票名称:</th>
			<td><input type="text"class="formText {required: true}" 
				name="name" value="${(ticket.name)!''}" /></td>
		</tr>
		<tr>
			<th>规则(几个月)</th>
			<td><input type="text"class="formText {required: true}" 
				name="rule" value="${(ticket.rule)!''}" /></td>
		</tr>
			<tr>
			<th>金额</th>
			<td><input type="text"class="formText {required: true,min: 0}" 
				name="realPrice" value="${(ticket.realPrice)!''}" /></td>
		</tr>
		<tr>
			<th>类型</th>
			<td><@u.select_map name="type" listKey="" listValue="" 
							list=ViewStaticModels.getTicketTypes() value="${(ticket.type)!'ANNUAL'}"/></td>
		</tr>
		<tr>
			<th>是否有效:</th>
			<td><@u.radio name="isEnabled" list={"true":"有效", "false": "无效"} 
					value="${(ticket.isEnabled?string('true', 'false'))!'false'}"/>
			</td>
		</tr>
		<tr>
			<th>描述</th>
			<td><textarea name="description" class="text" style="width:300px;height:56px">${(ticket.description)!''}</textarea></td>
		</tr>
	</table>
</form>
</body>
</html>