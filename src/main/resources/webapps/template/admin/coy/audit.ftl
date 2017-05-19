<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>list</title>
[#include "/admin/include/head.htm"]
<script type="text/javascript">
    var grid;
	
    $(function () {
        grid = $("#maingrid").ligerGrid({
            columns: [
			{name: 'id', hide: true, width:0, isAllowHide: false},
			{display: '申请时间', name: 'createDate', align: 'left', width: 250 }, 
			{display: '公司名称', name: 'companyName', align: 'left', width: 120 }, 
			{display: '手机', name: 'phone', align: 'left', width: 120 }
			],  pageSize:20, url: '${base}/admin/coy/audit/queryData.jhtml',
			toolbar: {}, width: '100%', height:'100%', enabledSort: true
        });
        $("#pageloading").hide();
		LG.loadToolbar(grid);
		
		//绑定搜索按钮
		LG.search(grid);
    });
	
</script>
</head>
<body style="padding:10px; overflow:hidden;">
	<div id="functions">
		<div class="clearfix">
			<div style="float:left;">
				<span id="searchDefault">
					手机号码:<input type="text" class="formText" name="search_EQ_phone"/>
				</span>
				<input type="button" id="search" class="button" value="搜索"/>
				<a href="#" id="search_btn_reset" face="Wingdings 3">解除搜索,显示全部数据</a>
			</div>
		</div>
	</div>
	<div id="maingrid" style="margin:0; padding:0"></div>
</body>
</html>