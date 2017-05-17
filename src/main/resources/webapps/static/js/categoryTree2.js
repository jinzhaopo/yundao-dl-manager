/**
 * 选择商品分类2
 */
var zTree1 = null;
$(document).ready(
	function(){
	$("#categoryTree").height($(window).height());	
	zTree1 = $.fn.zTree.init($("#categoryDemo"), setting);
});
var setting = {
	async: {
		enable: true,
		url: base + "/admin/goodsCategory/treeData.jhtml",
		autoParam: ["id=parentId"]
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
		onAsyncSuccess: onGoodsCategoryAsyncSuccess
	}
};
function onGoodsCategoryAsyncSuccess(){
	if(parent.onGoodsCategoryAsyncSuccess){
		parent.onGoodsCategoryAsyncSuccess();
	}
}
//nodeIds是字符串数组
function checkTree(nodeIds){
	for (var i = 0; i < nodeIds.length; i++) {
		var curNode = zTree1.getNodeByParam("id", nodeIds[i], null);
		zTree1.checkNode(curNode, true, false);
	}
}