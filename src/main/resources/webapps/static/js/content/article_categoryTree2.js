/**
 * 选择单个文章类别
 */
var zTree1 = null;
$(document).ready(function(){
	$("#articleCategoryTreeFrame").height($(window).height());
	zTree1 = $.fn.zTree.init($("#treeDemo"), setting);
});
var setting = {
	async: {
		enable: true,
		url: base + "/admin/articleCategory/treeData.jhtml",
		autoParam: ["id=pId"]
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
		url = base + "/admin/article/list.jhtml?id=" + treeNode.id;
	}else{
		url = base + "/admin/article/list.jhtml";
	}
	parent.rightFrame.location = url;
}