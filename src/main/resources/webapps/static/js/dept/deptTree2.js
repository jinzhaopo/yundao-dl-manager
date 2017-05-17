/**
 * 选择单个部门
 */
var zTree1 = null;
$(document).ready(function(){
	//initBody();
	$("#deptTree").height($(window).height());
	//$(window).resize(initBody);
	zTree1 = $.fn.zTree.init($("#treeDemo"), setting);
});
var setting = {
	async: {
		enable: true,
		url: base + "/admin/dept/treeData.jhtml",
		autoParam: ["id=parentId"]
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