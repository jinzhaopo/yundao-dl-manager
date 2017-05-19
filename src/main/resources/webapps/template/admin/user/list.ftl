<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
<#include "/admin/include/head.htm">
<script type="text/javascript">
    var grid;
	/**
	 * 新增账号
	 */
	function addUser(){
		top.f_addTab(null, '新增账号', base + '/admin/user/add.jhtml');
	}
	
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '登陆账号', name: 'userName', align: 'left', width: 120 },
			{display: '姓名', name: 'realName', align: 'left', minWidth: 60 },
            {display: '部门', name: 'deptName', align: 'left', width: 150 },
            {display: '角色名称', name: 'roleName', align: 'left', width: 250 },
			{display: '禁止账户登入', name: 'isAccountEnabled', align: 'center', dict:'common', width: 120 }
			],  pageSize:20, url: '${base}/admin/user/queryData.jhtml',
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
    });
	
	function f_delete() {
		var selected = grid.getSelected();
      	if (selected) {
          	LG.ajax({
              	url: base + '/admin/user/delete.jhtml',
              	loading: '正在删除中...',
              	data: { ids: selected.id },
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
					登陆账号:<input type="text" class="formText" name="search_LIKE_userName"/>
				</span>
				<input type="button" id="search" class="button" value="搜索"/>
				<a href="#" id="search_btn_reset" face="Wingdings 3">解除搜索,显示全部数据</a>
			</div>
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>