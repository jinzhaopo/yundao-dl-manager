var navtab = null;
$(function () {
	var $goodsAttributeTable = $("#goodsAttributeTable");
	var $addGoodsAttributeButton = $("#addGoodsAttributeButton");
	var $goodsParameterTable = $("#goodsParameterTable");
	var $addGroupButton = $("#addGroupButton"); //增加参数组按钮
	var $addParam = $(".addParam"); //增加参数按钮
	var $goodsParamContainer = $("#goodsParamContainer");
	var $deleteGroup = $(".deleteGroup");
	var $deleteParam = $(".deleteParam");
	var $deleteProps = $(".deleteProps");
	$("#tab").tabs("div.tabContent", {
		tabs: "input"
	});
	
	var goodsAttributeIndex = 0;
	function addGoodsAttribute(goodsAttribute){
		 var goodsAttributeTrHtml = '<tr class="tr_prop">' + 
				'<td> <input type="text" name="propnames" class="formText propnames" /></td>' + 
				'<td> <select name="proptypes" class="proptypes"> ' +
				'<optgroup label="输入项">' + 
					'<option value="input_no">输入项 不可搜索</option>' +
					'<option value="input_search">输入项 可搜索</option>' +
				'</optgroup>' + 
				'<optgroup label="选择项">' + 
					'<option value="select_progressive">选择项 渐进式搜索</option>' +
					'<option value="select_normal">选择项 普通搜索</option>' + 
					'<option value="select_no">选择项 不可搜索</option>' +
				'</optgroup>' + 
				'</select></td>'+
				'<td> <input type="text" name="options" class="formText optionText options" title="多个可选值.请使用“,”分隔" /> </td>' + 
				'<td> <select name="required"> ' +
					'<option value="0">否</option>' +
					'<option value="1">是</option>' +
				'</select></td>'+
				'<td> <select name="datatype"> ' +
					'<option value="string">字符串</option>' +
					'<option value="int">整型</option>' +
					'<option value="float">浮点型</option>' +
				'</select></td>'+
				'<td> <span class="deleteIcon deleteProps" title="删 除">&nbsp;</span></td></tr>';
        if ($goodsAttributeTable.find(".goodsAttributeTr").length >= 20) {
            $.dialog({
                type: "warn",
                content: "商品属性个数超出限制!",
                modal: true,
                autoCloseTime: 3000
            });
        } else {
            $goodsAttributeTable.append(goodsAttributeTrHtml);
			if (addGoodsAttribute) {
				var lastTd = $goodsAttributeTable.find("tr").last();
				lastTd.find(".goodsAttributeListName").val(goodsAttribute.name);
				lastTd.find(".attributeType").val(goodsAttribute.attributeType);
				lastTd.find(".goodsAttributeListOptionText").val(goodsAttribute.optionText);
				lastTd.find(".goodsAttributeListOrderList").val(goodsAttribute.orderBy);
			}
            goodsAttributeIndex++;
        }
	}
	// 增加商品属性
    $addGoodsAttributeButton.click(addGoodsAttribute);
	$deleteProps.live("click", function(){
		$(this).parent().parent().remove();
	});
	
	// 修改商品属性类型
    $("#goodsAttributeTable .attributeType").live("click", function(){
        var $this = $(this);
        var $optionText = $this.parent().parent().find(".optionText")
        if (!$this.val().startWith("input")) {
            $optionText.attr("disabled", false).show();
        } else {
            $optionText.attr("disabled", true).hide();
        }
    });
    // 删除商品属性
    $("#goodsAttributeTable .deleteGoodsAttributeIcon").live("click", function(){
        $(this).parent().parent().remove();
    });
	
	var groupParamTrHtmlPart = 
			'<tr class="groupTr">' + 
				'<th class="groupTrName">参数组称</th>' + 
				'<td><input type="text" name="groupnames" maxlength="60"/></td>' +
				'<td width="64%;">' + 
					'<span class="deleteGroup deleteIcon" title="删 除">&nbsp;</span>' + 
					'<a href="#" class="addParam" style="float:left;">增加参数</a>' + 
				'</td>' + 
			'</tr>';
			
	var paramTrHtml = 
			'<tr>' + 
				'<th class="paramName">参数名</th>' + 
				'<td><input type="text" name="paramnames"/></td>' + 
				'<td><span class="deleteParam deleteIcon" title="删 除">&nbsp;</span></td>'
			'</tr>';
	// 增加参数组
	function addGroupParam(goodsParameter){
        var groupParamTrHtml = '<table class="table parameterTable">' + groupParamTrHtmlPart + paramTrHtml + '</table>';
        $goodsParamContainer.append(groupParamTrHtml);
		if (goodsParameter) {
			var lastTd = $goodsParameterTable.find("tr").last();
			lastTd.find(".goodsParameterListName").val(goodsParameter.name);
			lastTd.find(".goodsParameterListOrderList").val(goodsParameter.orderBy);
		}
	}
	
	// 增加参数
	function addParam(goodsParameter){
		$(this).parent().parent().parent().append(paramTrHtml);
	}
	
    $addGroupButton.click(addGroupParam);
	//删除参数组
	$deleteGroup.live("click", function(){
		$(this).parent().parent().parent().parent().remove();
	});
	$addParam.live("click", addParam);
	//删除参数,如果参数组中只有一个参数，则不能删除
	$deleteParam.live("click", function(){
		if($(this).parent().parent().parent().find(".paramName").size() == 1){
			alert("参数组中必须至少存在一个参数!");
			return;
		}
		$(this).parent().parent().remove();
	});
	
	// 表单验证
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
    $validateForm.validate({
        errorContainer: $validateErrorContainer,
        errorLabelContainer: $validateErrorLabelContainer,
        wrapper: "li",
        errorClass: "validateError",
        ignoreTitle: true,
        rules: {
            "goodsType.name": "required",
			"propnames": "required",
			"groupnames": "required",
			"paramnames": "required"
        },
        messages: {
            "goodsType.name": {
                required: "请填写类型名称"
            },
			"propnames": {
				required: "请填写属性名"
			},
			"groupnames": {
				required: "请填写参数组名称"
			},
			"paramnames": {
				required: "请填写参数名称"
			}
        },
        submitHandler: function(form){
            $(form).find(":submit").attr("disabled", true);
            form.submit();
        }
    });
});

//设置参数组的个数
function setParamNum(){
    $("#paramnum").val("");
    var param_div = document.getElementById("goodsParamContainer");
    var tbls = param_div.getElementsByTagName("table");
    var paramnum = document.getElementById("paramnum").value;
    for (var i = 0; i < tbls.length; i++) {
        var texts = tbls[i].getElementsByTagName("input");
        if (paramnum != '') {
            paramnum += ",";
        }
        paramnum += (texts.length - 1);
    }
    document.getElementById("paramnum").value = paramnum;
    //选中品牌
    selectChoose();
    return true;
}

function selectBrand(){
	var selectBrandText = $("#selectBrandText").val();
	var sel_options=$("#brand_sel").find("option");
	var flag = false;
	//先去除所有的选中
	sel_options.each(function(){
		$(this).attr("selected", "");
	});
	//根据输入值搜索并选中
	sel_options.each(function(){
		if($(this).text().indexOf(selectBrandText) != -1){
			$(this).attr("selected", "true");
			flag = true;
		}
	});
	if(!flag){
		alert("没有该品牌");
	}
}
function contains(obj_sel, option){
	var options = obj_sel.options;
	for(var i=0;i<options.length;i++){
		if(options[i].value == option.value){
			return true;
		}
	}
	return false;
}

function add(text, value){
	var choose_sel = document.getElementById("choose_sel");
	var s = choose_sel.options.length;
	for(var i=0; i<choose_sel.length; i++) {
		var option  = new Option(text, value);
		if(contains(choose_sel, option)) {
			return;
		}
	}
	choose_sel.options[s++] = new Option(text, value);
}

function remove(){
	$("#choose_sel>option").each(function(){
		var option= $(this);
		if(option.attr("selected")){
			option.remove();
		}
	});
}

function clean(){
	$("#choose_sel>option").each(function(){
		 $(this).remove();
	});
}

function selectChoose(){
	$("#choose_sel>option").attr("selected","true");
	return true;
}