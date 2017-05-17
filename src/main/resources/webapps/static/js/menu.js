/** 菜单类型：无子菜单*/
var MENUTYPE_NODE = "MENUTYPE_NODE";
/** 菜单类型：分割线*/
var MENUTYPE_LINE = "MENUTYPE_LINE";
/**
 * 显示隐藏动作输入框
 */
function showHideActionRow(o){
    if (o.value == MENUTYPE_NODE) {
		$("tr[id^=menu]").css("display", "");
		$("tr#_menuName").css("display", "");
        $("#input[name=menu.operate.url]").removeAttr("disabled");
    } else if(o.value == MENUTYPE_LINE){
		$("tr[id^=menu]").css("display", "none");
		$("tr#_menuName").css("display", "none");
    }else{
		$("tr[id^=menu]").css("display", "none");
		$("tr#_menuName").css("display", "");
        $("#input[name=menu.operate.url]").attr("disabled", true);
    }
}

/**
 * 新增菜单表单校验
 */
function ValidateFrm(){	
    var oMenuName = $("input[name=menu.name]");
    if (oMenuName.val().trim().length == 0) {
		oMenuName.focus();
        window.alert("菜单名不能为空！");
        return false;
    }
    return true;
}
