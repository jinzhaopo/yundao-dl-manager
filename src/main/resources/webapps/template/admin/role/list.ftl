<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
</head>
<body>
<script type="text/javascript">
    var grid;
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '角色名称', name: 'name', align: 'left', minWidth: 60 },
			{display: '系统内置', name: 'isSystem', align: 'center', dict:'common', width: 60 },
            {display: '描述', name: 'description', align: 'center', width: 120},
            {display: '创建时间', name: 'createDate', align: 'center', width: 200 }
            ],  pageSize:20,
            url: '${base}/admin/role/queryData.jhtml',
			toolbar: {}, width: '100%', height:'100%'
        });
		$("#pageloading").hide();
		LG.loadToolbar(grid);
		//绑定搜索按钮
		LG.search(grid);
    });

	function f_delete() {
		var selecteds = grid.getSelecteds();
	    if (selecteds.length > 0) {
			LG.ajax({
				url: base + '/admin/role/deleteRole.jhtml',
	            loading: '正在删除中...',
	            data: { ids: LG.getIds(selecteds) },
	            success: function () {
					LG.showSuccess('删除成功');
	                f_reload();
	            },
	            error: function (message) {
					LG.showError(message);
	            }
	        });
		} else {
			LG.tip('请选择行!');
	    }
	}
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="functions">
		<div class="clearfix">
			<div style="float:left;">
				<span id="searchDefault">
					角色名称:<input type="text" class="formText" name="search_LIKE_name"/>
				</span>
				<input type="button" id="search" class="button" value="搜索"/>
				<a href="#" id="search_btn_reset" face="Wingdings 3">解除搜索,显示全部数据</a>
			</div>
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>