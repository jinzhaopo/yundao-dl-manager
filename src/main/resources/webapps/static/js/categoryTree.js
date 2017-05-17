/**
 * 选择商品分类
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
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: clickNode
	}
};

//选中事件处理
function clickNode(event, treeId, treeNode, clickFlag){
	var url;
	if(treeNode.id){
		url = base + "/admin/goodsCategory/edit.jhtml?goodsCategory_id=" + treeNode.id;
	}else{
		url = base + "/admin/goodsCategory/edit.jhtml";
	}
	parent.rightFrame.location = url;
}
//新增节点
function addNode(newNodes, isSilent){
	var parentNode = zTree1.getNodeByParam("id", newNodes.parentId, null);
	zTree1.addNodes(parentNode, newNodes, isSilent);
}
//删除节点
function removeNode(id){
	var curNode = zTree1.getNodeByParam("id", id, null);
	zTree1.removeNode(curNode);
}
//更新节点
function updateNode(newNode){
	var curNode = zTree1.getNodeByParam("id", newNode.id, null);
	curNode.name=newNode.name;
	curNode.type=newNode.type;
	zTree1.updateNode(curNode);
}
