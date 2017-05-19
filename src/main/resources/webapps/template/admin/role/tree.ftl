<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色管理</title>
<#include "/admin/include/treeHead.htm">
<script type="text/javascript" src="${base}/resource/js/roleTree.js"></script>
<script type="text/javascript">
	$(function(){
		<#if Parameters.isEdit?? && Parameters.isEdit == "true">
		toCheckNode("${(Request["role.id"])!''}");
		</#if>
		//var parentHeight = $(parent.window).height() - $(parent.document.getElementById("mainContainer")).height()-15;
		//$("#roleTree").height(parentHeight);
	});
</script>
</head>
<body>
	<div id="roleTree" style="overflow-y:auto;">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</body>
</html>