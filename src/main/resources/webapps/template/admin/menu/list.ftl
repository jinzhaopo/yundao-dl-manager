<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>菜单管理</title> [#include "/admin/include/head.htm"]
</head>
<body>
	<script type="text/javascript">
		//设置为自动刷新
		setInterval("refersh_grid()", 3000);
		var grid;
		$(function() {
			grid = $("#maingrid").ligerGrid({
				columns : [ {
					name : 'id',
					hide : true,
					width : 0,
					isAllowHide : false
				}, {
					display : '资源名称',
					name : 'name',
					align : 'left',
					minWidth : 60
				}, {
					display : '资源值',
					name : 'value',
					align : 'center',
					width : 120
				}, {
					display : '系统内置',
					name : 'isSystem',
					align : 'center',
					width : 60,
					dict : 'common'
				}, {
					display : '排序',
					name : 'orderList',
					align : 'left',
					width : 150
				} ],
				pageSize : 20,
				url : 'operate!queryData.action',
				toolbar : {},
				width : '100%',
				height : '100%',
				enabledSort : true
			});

			//双击事件(id:为点击操作的id)
			LG.setGridDoubleClick(grid, 445);
		});
	</script>
	</head>
	<body style="padding: 10px; overflow: hidden;">
		<div id="functions">
			<div class="clearfix">
				<div style="float: left;">
					<span id="searchDefault"> 资源名称:<input type="text"
						class="formText" name="resource.name" />&nbsp; 资源值:<input
						type="text" class="formText" name="resource.value" />
					</span> <input type="button" id="search" class="button" value="搜索" /> <#--<img
						src="${base}/core_res/common/img/zoom2.gif" /> <a href="#"
						id="advanceSearch">切换到高级查询</a>--> <a href="#"
						id="search_btn_reset" face="Wingdings 3">解除搜索,显示全部数据</a>
				</div>
			</div>
		</div>
		<div id="maingrid" style="margin: 0; padding: 0"></div>
	</body>
</html>