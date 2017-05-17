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
	}
};

//选中事件处理
function getSelectNode(event, treeId, treeNode, clickFlag){
	return zTree1.getSelectedNodes()[0];
}