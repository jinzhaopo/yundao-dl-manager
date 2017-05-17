/**
 * 选择单个部门
 */
var zTree1 = null;
$(document).ready(function(){
	//$("#roleTree").height($(window).height());	
	zTree1 = $.fn.zTree.init($("#treeDemo"), setting);
});
var setting = {
		
	async: {
		enable: true,
		url: base + "/admin/role/getPrivTree.jhtml",
		autoParam: ["id=parentId"]
	},
	check: {
		enable: true,
		chkboxType: { "Y" : "ps", "N" : "ps" }
	},
	data: {
		simpleData: { 
			enable: true,
			idKey: "idKey",
			pIdKey: "pIdKey",
			rootPId: null
		}
	},
	callback: {
		onClick: clickNode,
		onAsyncSuccess: checkNodeTree
	}
};

//选中事件处理
function clickNode(event, treeId, treeNode, clickFlag){
}

function toCheckNode(role_id){
	LG.ajax({
		url: base + "/admin/role/priv.jhtml",
		data:{"id": role_id},
		success: function(data){
			if(data.menuIds){
				for(var i=0; i<data.menuIds.length; i++){
					checkNode(data.menuIds[i]);
					
				}
			}
			
			if(data.operateIds){
				for(var i=0; i<data.operateIds.length; i++){
					var node = zTree1.getNodeByParam("idKey", "o"+data.operateIds[i], null);
					zTree1.checkNode(node, true, false);
				}
			}
		}
	});
}

function checkNode(node_id){
	var node = zTree1.getNodeByParam("id", node_id, null);
	zTree1.checkNode(node, true, false);
}

function getCheckedMenuNodes(){
	var tmp = zTree1.getCheckedNodes();
	var moduleArray = new Array();
	for (var i = 0; i < tmp.length; i++) {
		if (tmp[i].type == "menu") {
			moduleArray.push(tmp[i]);
		}
	}
	return moduleArray;
}

function getCheckedOperateNodes(){
	var tmp = zTree1.getCheckedNodes();
	var operateArray = new Array();
	for (var i = 0; i < tmp.length; i++) {
		if (tmp[i].type == "operate") {
			operateArray.push(tmp[i]);
		}
	}
	return operateArray;
}