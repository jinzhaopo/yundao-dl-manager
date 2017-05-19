<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>给用户${(s_user.real_name)!''}绑定角色</title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
	var grid1, grid2;
	$(function () {
        grid1 = $("#maingrid1").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '中文名称', name: 'name', align: 'left', width: 100 },
			{display: '角色名称', name: 'value', align: 'left', minWidth: 60 }
			],  usePager:false, url: 'userRoles.jhtml?user_id=' + getQueryStringByName("s_user.id"),
			toolbar: null, width: '100%', height:'100%', 
			title: "<img src='${base}/resource/images/communication.gif'>&nbsp;&nbsp;&nbsp;&nbsp;当前用户已绑定的角色"
        });
        
		grid2 = $("#maingrid2").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '中文名称', name: 'name', align: 'left', width: 100 },
			{display: '角色名称', name: 'value', align: 'left', minWidth: 60 }
			],  usePager:false, url: 'notUserRoles.jhtml?user_id=' + getQueryStringByName("s_user.id"),
			toolbar: null, width: '100%', height:'100%', 
			title: "<img src='${base}/resource/images/communication.gif'>&nbsp;&nbsp;&nbsp;&nbsp;当前用户未绑定的角色"
        });
    });
	
	function moveRoleLeft() {
		var rows = grid2.getSelecteds();
		if (rows.length == 0) {
			LG.showError("请在未绑定角色列表中选择角色!");
		}
		var roleIds = new Array();
		for (var i = 0; i < rows.length; i++) {
			roleIds.push(rows[i].id);
		}
		if(confirm('确认设置', '您确定设置该按钮?')){
			LG.ajax({
				url : "moveRoleLeft.jhtml",
				data : {"roleIds": roleIds, "user_id": getQueryStringByName("s_user.id")},
				success: function(){
					f_reload("maingrid1");
					f_reload("maingrid2");
				}
			});
		}
	}
	
	function moveRoleRight() {
		var rows = grid1.getSelecteds();
		if (rows.length == 0) {
			LG.showError("请在未绑定角色列表中选择角色!");
		}
		var roleIds = new Array();
		for (var i = 0; i < rows.length; i++) {
			roleIds.push(rows[i].id);
		}
		if(confirm('确认设置', '您确定设置该按钮?')){
			LG.ajax({
				url : "moveRoleRight.jhtml",
				data : {"roleIds": roleIds, "user_id": getQueryStringByName("s_user.id")},
				success: function(){
					f_reload("maingrid1");
					f_reload("maingrid2");
				}
			});
		}
	}
</script>
</head>
<body>
<div id="tabcontainer" style="margin:10px;">
	<div style="width:45%;float:left;">
		<div id="maingrid1" style="margin:0; padding:0;"></div>
	</div>
	<div style="width:10%;float:left;vertical-align:middle;height:100%;top:50%;text-align:center;">
		<input value="<<左移" type="button" onclick="moveRoleLeft();"/>
		<input value="右移>>" type="button" onclick="moveRoleRight();"/>
	</div>
	<div style="width:45%;float:left;">
		<div id="maingrid2" style="margin:0; padding:0;"></div>
	</div>
	<div style="clear:both;width:100%;height:0px;"></div>
</div>
</body>
</html>