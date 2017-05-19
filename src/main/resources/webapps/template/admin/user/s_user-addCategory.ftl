<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>给用户${(s_user.s_employee.real_name)!''}绑定商品分类</title>
<#include "/WEB-INF/template/admin/include/head.htm">
<script type="text/javascript">
	var grid1, grid2;
	$(function () {
        grid1 = $("#maingrid1").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{name: 'treeLevel', hide: true, width:0, isAllowHide: false},
			{display: '分类名称', name: 'name', align: 'left', width: 300 ,			
				render: function (row){
					return '<span style="margin-left:' + row.treeLevel * 20 + 'px;">' + row.name + '</span>';
               		}
				}				
			],  usePager:false, url: 's_user!userCategory.action?s_user.id=' + getQueryStringByName("s_user.id"),
			toolbar: null, width: '100%', height:'100%', 
			title: "<img src='${base}/core_res/common/img/communication.gif'>&nbsp;&nbsp;&nbsp;&nbsp;当前用户已绑定的商品分类"
        });
        
		grid2 = $("#maingrid2").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{name: 'treeLevel', hide: true, width:0, isAllowHide: false},
			{display: '分类名称', name: 'name', align: 'left', width: 300 }
			],  usePager:false, url: 's_user!notUserCategory.action?s_user.id=' + getQueryStringByName("s_user.id"),
			toolbar: null, width: '100%', height:'100%', 
			title: "<img src='${base}/core_res/common/img/communication.gif'>&nbsp;&nbsp;&nbsp;&nbsp;当前用户未绑定的商品分类"
        });
    });
	
	function moveCategoryLeft() {
		var rows = grid2.getSelecteds();
		if (rows.length == 0) {
			LG.showError("请在未绑定商品分类列表中选择分类!");
		}
		var categoryIds = new Array();
		for (var i = 0; i < rows.length; i++) {
			categoryIds.push(rows[i].id);
		}
		if(confirm('确认设置', '您确定设置该按钮?')){
			LG.ajax({
				url : "s_user!moveCategoryLeft.action",
				data : {"categoryIds": categoryIds, "s_user.id": getQueryStringByName("s_user.id")},
				success: function(){
					f_reload("maingrid1");
					f_reload("maingrid2");
				}
			});
		}
	}
	
	function moveCategoryRight() {
		var rows = grid1.getSelecteds();
		if (rows.length == 0) {
			LG.showError("请在未绑定商品分类列表中选择分类!");
		}
		var categoryIds = new Array();
		for (var i = 0; i < rows.length; i++) {
			categoryIds.push(rows[i].id);
		}
		if(confirm('确认设置', '您确定设置该按钮?')){
			LG.ajax({
				url : "s_user!moveCategoryRight.action",
				data : {"categoryIds": categoryIds, "s_user.id": getQueryStringByName("s_user.id")},
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
		<input value="<<左移" type="button" onclick="moveCategoryLeft();"/>
		<input value="右移>>" type="button" onclick="moveCategoryRight();"/>
	</div>
	<div style="width:45%;float:left;">
		<div id="maingrid2" style="margin:0; padding:0;"></div>
	</div>
	<div style="clear:both;width:100%;height:0px;"></div>
</div>
</body>
</html>