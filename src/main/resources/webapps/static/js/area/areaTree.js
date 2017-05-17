/**
 * 选择区域
 */
var zTree1 = null;
$(document).ready(
	function(){
	$("#areaTree").height($("#areaTree").height());	
	zTree1 = $.fn.zTree.init($("#areaTree"), setting);
});
var setting = {
	async: {
		enable: true,
		url: base + "/admin/area/getChildren.jhtml",
		autoParam: ["id=parentId", "checked"]
	},
	check: {
		enable: true,
		chkboxType:{ "Y": "ps", "N": "ps" }
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onAsyncSuccess: onAreaAsyncSuccess
	}
};

//选中事件处理
function clickNode(event, treeId, treeNode, clickFlag){
}
//区域加载成功后选中
function onAreaAsyncSuccess(){
	if($("#areaIds").size() > 0 && $("#areaIds").val().trim() != ""){
		var areaIds = $("#areaIds").val().split(",");
		checkTree(areaIds);
	}
}
//nodeIds是字符串数组
function checkTree(nodeIds){
	for (var i = 0; i < nodeIds.length; i++) {
		var curNode = zTree1.getNodeByParam("id", nodeIds[i], null);
		zTree1.checkNode(curNode, true, true);
	}
}
