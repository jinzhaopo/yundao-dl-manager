<!DOCTYPE html >
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="${base}/resource/css/list.css" rel="stylesheet" type="text/css" />
[#include "/admin/include/head.htm"]
<script type="text/javascript" src="${base}/resource/js/area/list.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $delete = $(".delete");
	
	// 地区删除
	$delete.click( function() {
		var $this = $(this);
		var id = $this.metadata().id;
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: "delete.jhtml",
				data: {"id": id},
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.type == "SUCCESS") {
						$this.parent().html("&nbsp;");
					}
					alert(data.content);
				}
			});
		}
		return false;
	});
	
})
</script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>地区管理&nbsp;<span class="pageInfo">总记录数: ${areaList?size}</span></h1>
		</div>
		<form id="listForm" action="list.jhtml" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='add.jhtml[#if parent??]?parentId=${parent.id}[/#if]'" value="添加地区" />
			</div>
			<table class="listTable">
				<tr>
					<th colspan="5" class="green" style="text-align: center;">
						[#if parent??]上级地区 - [${(parent.name)!}][#else>顶级地区[/#if>
					</th>
				</tr>
				[#list areaList as list]
					[#if (list_index + 1) == 1]
						<tr>
					[/#if]
					<td>
						<a href="list.jhtml?parentId=${list.id}" title="查看下级地区">${list.name}</a>
						<a href="edit.jhtml?id=${list.id}" title="编辑">[编辑]</a>
						<a href="#" class="delete {id: '${list.id}'}" title="删除">[删除]</a>
					</td>
					[#if (list_index + 1) % 5 == 0 && list_has_next]
						</tr>
						<tr>
					[/#if]
					[#if (list_index + 1) % 5 == 0 && !list_has_next]
						</tr>
					[/#if]
					[#if (list_index + 1) % 5 != 0 && !list_has_next]
							<td colspan="${5 - areaList?size % 5}">&nbsp;</td>
						</tr>
					[/#if]
				[/#list]
				[#if areaList?size == 0]
					<tr>
						<td colspan="5" style="text-align: center; color: red;">
							无下级地区! <a href="add.jhtml[#if parent??]?parentId=${parent.id}[/#if]" style="color: gray">点击添加</a>
						</td>
					</tr>
				[/#if]
			</table>
			[#if parent??]
				<div class="blank"></div>
				[#if (parent.parentId)??]
					<input type="button" class="formButton" onclick="location.href='list.jhtml?parentId=${(parent.parentId)!}'" value="上级地区" />
				[#else]
					<input type="button" class="formButton" onclick="location.href='list.jhtml'" value="上级地区" />
				[/#if]
			[/#if]
		</form>
	</div>
</body>
</html>