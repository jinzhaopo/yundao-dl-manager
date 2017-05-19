<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		[#include "/admin/include/head.htm"]
		[@flash_message /]
		<script type="text/javascript">
			function deleteMenu(id) {
				if (confirm('此操作将删除菜单和相关的动作，确定删除吗?')) {
					LG.ajax({
						url : base + "/admin/menu/delete.jhtml",
						data : {
							"id" : id
						},
						success : function(result) {
							window.parent.leftFrame.removeNode(result.id);
							if(result.upMenuId != null){
								location.href = base + "/admin/menu/detail.jhtml?id=" + result.upMenuId;
							}else{
								location.href = base + "/admin/menu/detail.jhtml?id=";
							}
						}
					});
				}
			}

			/**
			 * 保存操作的排序
			 */
			function savePriority() {
				var operateIdList = $("#authorTable input[name='operateList.id']");
				var operatePriorityList = $("#authorTable input[name='operateList.priority']");
				var ids = new Array();
				var priority = new Array();
				operateIdList.each(function() {
					ids.push($(this).val());
				});
				operatePriorityList.each(function() {
					priority.push($(this).val());
				});
				LG.ajax({
					url : base + "/admin/operate/updateOperatePriority.jhtml",
					async : true,
					data : {
						"ids" : ids,
						"priority" : priority,
						"menu.id" : "${(menu.id)!''}"
					},
					success : function(result) {
						if (result.type == "SUCCESS") {
							alert(result.content);
							location.reload();
						}
					}
				});

			}

			/**
			 * 保存菜单操作的排序
			 */
			function saveMenuPriority() {
				var submenuIdList = $(".menu_detail_table input[name='id']");
				var submenuPriorityList = $(".menu_detail_table input[name='priority']");
				var ids = new Array();
				var priority = new Array();
				submenuIdList.each(function() {
					ids.push($(this).val());
				});
				submenuPriorityList.each(function() {
					priority.push($(this).val());
				});
				LG.ajax({
					url : base + "/admin/menu/updateMenuPriority.jhtml",
					async : true,
					data : {
						"ids" : ids,
						"priority" : priority,
						"menu.id" : "${(menu.id)!''}"
					},
					success : function(result) {
						if (result.type == "SUCCESS") {
							alert(result.content);
							location.reload();
						}
					}
				});

			}
		
		[#if menu.id??]
		[@rbac method="getOperatOfMenu" param="{id:"+menu.id+"}"]
			[#if tag_bean??]
				[#assign operateId = tag_bean.id /]
				[#assign operateURL = tag_bean.url /]
			[/#if]
		[/@rbac ]
		[/#if]
		</script>
	</head>
	<body>
		<div style="margin:0 auto;width:98%;" >
			<table width="100%" align="center" border="0">
				<tr>
					<td> 当前菜单： <span style="font-weight:bold;color:#000099">${menu.name!''}</span></td>
				</tr>
			</table>
			<table width="100%" align="center" class="menu_detail_table" style="margin-top:15px">
				<tr>
					<th>菜单类型</th>
					<th>菜单动作</th>
					<th>说明</th>
					<th>父菜单</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>${menu.type.typeName!''}</td>
					<td style="word-wrap:break-word;">${operateURL!''}</td>
					<td>${menu.description!''}</td>
					<td> 
						[#if menu.upMenuId??] 
							<a href="detail.jhtml?id=${menu.upMenuId!''}">${(menu.upMenuId)!''}</a> 
						[#else] 
							<a href="detail.jhtml?id=${menu.genRoot().id!''}">${(menu.genRoot().name)!''}</a> 
						[/#if] 
					</td>
					<td> 
						[#if menu.id??] 
							<a href="${base}/admin/menu/update/${menu.id}.jhtml">编辑</a>
							<a href="#" onclick="deleteMenu(${menu.id});">删除</a> 
						[/#if]
					[#if menu.type == 'MENUTYPE_PARENT' && (menu.id)??] <a href="add.jhtml?parent_id=${menu.id!''}">添加子菜单</a> [#elseif !menu.id??] <a href="add.jhtml">添加子菜单</a> [/#if]
					[#if menu.type == 'MENUTYPE_NODE'] <a href="${base}/admin/operate/add.jhtml?id=${menu.id!''}">添加动作</a> [/#if] </td>
				</tr>
			</table>

			[#if subMenuList?? && subMenuList?size &gt; 0]
			<table width="100%" border="0" style="margin-top:15px">
				<tr>
					<td> 子菜单列表：
					<input type="button" value="保存排序" onclick="saveMenuPriority();"/>
					</td>
				</tr>
			</table>
			<table width="100%" class="menu_detail_table">
				<tr>
					<th>菜单名称</th>
					<th>菜单类型</th>
					<th>菜单动作</th>
					<th>说明</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				[#list subMenuList as subMenu]
				<tr>
					<td><a href="detail.jhtml?id=${subMenu.id}">${subMenu.name}</a></td>
					<td>${subMenu.type.typeName!''}</td>
					<td>
						[@rbac method="getOperatOfMenu" param="{id:"+1+"}"]
							${(tag_bean.url)!''}
						[/@rbac]
					</td>
					<td>${subMenu.description!''}</td>
					<td>
					<input type="hidden" name="id" value="${(subMenu.id)!''}"/>
					<input type="text" name="priority" value="${(subMenu.priority)!'0'}" size="3"/>
					</td>
					<td> [#if subMenu.upMenuId??] <a href="delete/${subMenu.id}.jhtml" onclick="return confirm('此操作将删除所有相关子菜单或动作,确定要删除吗?');">删除</a>[/#if]
					[#if subMenu_index != 0] <a href="upMenu.jhtml?menu.id=${(subMenu.id)!''}">上移</a> [/#if]
					[#if subMenu_index != subMenuList.size() - 1] <a href="downMenu.jhtml?menu.id=${(subMenu.id)!''}">下移</a> [/#if] </td>
				</tr>
				[/#list]
			</table>
			[#elseif normalActionList?? || authorActionList??]
			<table width="100%" border="0" style="margin-top:15px">
				<tr>
					<td>普通动作列表：</td>
				</tr>
			</table>
			<table width="100%" class="menu_detail_table" style="table-layout:fixed;">
				<tr>
					<th>动作名称</th>
					<th>动作类型</th>
					<th>动作URL</th>
					<th>URL规则</th>
					<th>说明</th>
					<th>操作</th>
				</tr>
				[#list normalActionList as normalAction]
				<tr>
					<td>${(normalAction.name)!''}</td>
					<td>普通动作</td>
					<td style="word-wrap:break-word;"><a href="#">${(normalAction.url)!''}</a></td>
					<td style="word-wrap:break-word;">${(normalAction.pattern)!''}</td>
					<td>${(normalAction.description)!''}</td>
					<td> [#if operateId != normalAction.id] <a href="${base}/admin/operate/update/${(normalAction.id)!''}.jhtml">编辑</a><a href="${base}/admin/operate/delete/${(normalAction.id)!''}.jhtml" onclick="return confirm('此操作将删除指定动作，并且不能恢复,确定要删除吗?');">删除</a> [#else]
					(菜单动作，无法操作)
					[/#if] </td>
				</tr>
				[/#list]
			</table>
			<table width="100%" border="0" style="margin-top:15px">
				<tr>
					<td> 授权动作列表：
					<input type="button" value="保存排序" onclick="savePriority();"/>
					</td>
				</tr>
			</table>
			<table width="100%" class="menu_detail_table" id="authorTable" style="table-layout:fixed;">
				<tr>
					<th>动作名称</th>
					<th>动作类型</th>
					<th>动作URL</th>
					<th>URL规则</th>
					<th>说明</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
				[#list authorActionList as authorOperate]
				<tr>
					<td>${(authorOperate.name)!''}</td>
					<td>授权动作</td>
					<td style="word-wrap:break-word;"><a href="#">${(authorOperate.url)!''}</a></td>
					<td style="word-wrap:break-word;">${(authorOperate.pattern)!''}</td>
					<td>${(authorOperate.description)!''}</td>
					<td>
					<input type="hidden" name="operateList.id" value="${(authorOperate.id)!''}"/>
					<input type="text" name="operateList.priority" value="${(authorOperate.priority)!'0'}" size="3"/>
					</td>
					<td><a href="${base}/admin/operate/update/${(authorOperate.id)!''}.jhtml">编辑</a><a href="${base}/admin/operate/delete/${(authorOperate.id)!''}.jhtml" onclick="return confirm('此操作将删除指定动作，并且不能恢复,确定要删除吗?');">删除</a></td>
				</tr>
				[/#list]
			</table>
			[/#if]
		</div>
	</body>
</html>