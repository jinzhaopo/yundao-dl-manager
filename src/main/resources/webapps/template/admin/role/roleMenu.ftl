<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色管理</title>
<#include "/admin/include/treeHead.htm">
<script type="text/javascript">
	function checkNodeTree(){
		<#if isEdit?? && isEdit == "true">
		toCheckNode("${(RequestParameters["id"])!''}");
		</#if>
	}
	
	function save(){
		var checkedMenuNodes = getCheckedMenuNodes();
		var checkedOperateNodes = getCheckedOperateNodes();
		var menuIds = [], operateIds = [];
		for(var i=0; i<checkedMenuNodes.length; i++){
			menuIds[i] = checkedMenuNodes[i].id;
		} 
		for(var i=0; i<checkedOperateNodes.length; i++){
			operateIds[i] = checkedOperateNodes[i].id;
		}
		
		LG.ajax({
			url: base + "/admin/role/editRoleMenu.jhtml",
			data: {"id": getQueryStringByName("id"), 
				menuIds:menuIds, operateIds: operateIds},
			success: function(result){
				LG.showSuccess(result.content);
			}
		});
	}
</script>
<script type="text/javascript" src="${base}/resource/js/roleTree.js"></script>
</head>
<body>
	<div id="roleTree">
		<ul id="treeDemo" class="ztree" style="overflow:hidden;"></ul>
	</div>
	<div class="noDisplayBtn">
		<input type="button" id="doSubmit" onclick="save();"/>
	</div>
</body>
</html>