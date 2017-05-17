/*
    文件说明：
        icon选取

    接口方法：
        1，打开窗口方法：f_openIconsWin
        2，保存下拉框ligerui对象：currentComboBox

    例子：
        可以这样使用(选择ICON完了以后，会把icon src保存到下拉框的inputText和valueField)：
        onBeforeOpen: function ()
        {
            currentComboBox = this;
            f_openIconsWin();
            return false;
        }

*/

//图标
var jiconlist, winicons, currentComboBox;
$(function ()
{
    jiconlist = $("body > .iconlist:first");
    if (!jiconlist.length) jiconlist = $('<ul class="iconlist"></ul>').appendTo('body');
});
 
$(".iconlist li").live('mouseover', function ()
{
    $(this).addClass("over");
}).live('mouseout', function ()
{
    $(this).removeClass("over");
}).live('click', function ()
{
    if (!winicons) return;
    var src = $("img", this).attr("src");
    src = src.replace(/^([\.\/]+)/, '');
//    var editingrow = grid.getEditingRow();
//    if (editingrow)
//    {
        if (currentComboBox)
        {
			currentComboBox.value = src;
//            currentComboBox.inputText.val(src);
//            currentComboBox.valueField.val(src);
        }
//    }
    winicons.hide();
});

function f_openIconsWin()
{
    if (winicons)
    {
        winicons.show();
        return;
    }
    winicons = $.ligerDialog.open({
        title: '选取图标',
        target: jiconlist,
        width: 470, height: 280, modal: true
    });
    if (!jiconlist.attr("loaded"))
    {
        LG.ajax({
            url: base + '/admin/menu/getIcons.jhtml',
            loading: '正在加载图标中...',
            data: { HttpContext: true },
            success: function (data) {
                for (var i = 0, l = data.length; i < l; i++) {
					var src = data[i];
					var reg = /(resource\\thirdparty)(.+)/;
					var reg2 = /(resource\\common)(.+)/;
					var reg3 = /(resource\/thirdparty)(.+)/;
					var reg4 = /(resource\/common)(.+)/;
					var s = null, match = null;
					if (match = reg.exec(src)) {
						s = "../../resource/thirdparty" + match[2].replace(/\\/g, '/');
					}else if (match = reg2.exec(src)) {
						s = "../../resource" + match[2].replace(/\\/g, '/');
					}else if (match = reg3.exec(src)) {
						s = "../resource/thirdparty" + match[2].replace(/\\/g, '/');
					}else if (match = reg4.exec(src)) {
						s = "../../resource/common" + match[2].replace(/\\/g, '/');
					}else {
						return;
					}
					//                    var match = reg.exec(src);
					//                    if (!match) continue;
					//var s = "../core_res/thirdparty" + match[2].replace(/\\/g, '/');
					jiconlist.append("<li><img src='" + s + "' /></li>");
				}
                jiconlist.attr("loaded", true);
            }
        });
    }
}