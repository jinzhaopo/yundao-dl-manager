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
			//默认是管理员列表，如果传入参数s_employee_list，则根据参数刷新列表
			var parentMenuNo = getQueryStringByName("parentMenuNo");
			if(!parentMenuNo){
				parentMenuNo = "s_user_list";
			}
			f_save(mainform, parentMenuNo);
		});
	});
	
	function selectDept(){
		$.ligerDialog.open({ url: base + '/admin/dept/tree.jhtml?style=2', height: 400, width: 300, 
		buttons: [{ 
			text: '确定', onclick: function (item, dialog) { 
				var selectNode = dialog.frame.getSelectNode();
				if(!selectNode.id){
					LG.showError("请不要选择根景区!");
					return;
				}
				addRelativeToObject({"dept_id": selectNode.id, "dept_name": selectNode.name});
				dialog.close();
			} 
		},{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
         ], isResize: true
        });
	}
</script>
</head>
<#if !s_user??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<body scroll="no" style="padding:30px;overflow:hidden;">
<form action="<#if isAdd??>${base}/admin/user/add.jhtml<#else>${base}/admin/user/update.jhtml</#if>" method="post" id="mainform" class="validate">
	<#if !isAdd??>
		<input type="hidden" name="id" value="${(s_user.id)!''}"/>
	</#if>
	<table class="table">
		<tr>
			<th>名称:</th>
			<td><input type="text"class="formText {required: true}" 
				name="real_name" value="${(s_user.realName)!''}" /></td>
		</tr>
		<tr>
			<th>景区:</th>
			<td>
				<input type="text" class="formText {required: true}" id="dept_name" value="${(dept.name)!''}" 
						title="点击后弹出" onfocus="selectDept();" readonly="readonly"/>
				<input type="hidden" id="dept_id" name="dept.id" value="${(dept.id)!''}" />
			</td>
		</tr>
		<tr>
			<th>登陆账号:</th>
			<td>
				<#if isAdd??>
				<input type="text"class="formText {required: true, remote: 'checkUsername.jhtml?oldValue=${(s_user.userName)!}', messages: {remote: '登陆账号已存在!'}}" 
					name="userName" value="${(s_user.userName)!''}" />
				<#else>
					${(s_user.userName)!''}
				</#if>
			</td>
		</tr>
		<tr>
			<th>登陆密码:</th>
			<td><input type="password" class="formText" name="password" id="password"/></td>
		</tr>
		<tr>
			<th>重复密码:</th>
			<td><input type="password" name="repassword"
			 	class="formText {equalTo: '#password', messages:{equalTo: '重复密码不一致'}}"/></td>
		</tr>
		<tr>
			<th>角色:</th>
			<td>
				<#if isEdit??>
					<@u.radio listKey="id" listValue="name" name="roleId" list=allRoles value=s_user.roleId/>
				<#else>
					<@u.radio listKey="id" listValue="name" name="roleId" list=allRoles/>
				</#if>
			</td>
		</tr>
		<tr>
			<th>禁止账户登入:</th>
			<td><@u.radio name="isAccountEnabled" list={"true":"是", "false": "否"} 
					value="${(s_user.isAccountEnabled?string('true', 'false'))!'false'}"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>