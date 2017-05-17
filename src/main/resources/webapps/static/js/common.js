var setting = {
	priceScale: "2",
	priceRoundType: "roundHalfUp",
	currencySign: "￥",
	currencyUnit: "元",
	uploadImageExtension: "jpg,jpeg,bmp,gif,png",
	uploadFlashExtension: "swf,flv",
	uploadMediaExtension: "swf,flv,mp3,wav,avi,rm,rmvb",
	uploadFileExtension: "zip,rar,7z,doc,docx,xls,xlsx,ppt,pptx"
};
//=========================================================================
//Browser操作工具
//=========================================================================
var Browser = {
 isIE: function(version){
     if (navigator.userAgent.toLowerCase().indexOf("msie") == -1) {
         return false;
     } else {
         if (version) {
             var regexpr = new RegExp("msie\\s*" + version, "g");
             if (navigator.userAgent.toLowerCase().match(regexpr)) {
                 return true;
             } else {
                 return false;
             }
         } else {
             return true;
         }
     }
 }
};
//=========================================================================
//STRING操作工具
//=========================================================================
//去除所有空格
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
//去除换行
String.prototype.Trim_n = function(){
    return this.replace(/\r\n|\n/g, "");
};
String.prototype.startWith = function(str){
    if (typeof(str) === "undefined") {
        return false;
    }
    str = str.toString();
    if (this.substr(0, str.length) == str) {
        return true;
    }
    else {
        return false;
    }
};

String.prototype.endWith = function(otherStr){
    if (typeof(otherStr) === "undefined") {
        return false;
    }
    otherStr = otherStr.toString();
    var startPos = this.length - otherStr.length;
    if (startPos >= 0) {
        var tmp = this.substr(startPos);
        if (tmp === otherStr) {
            return true;
        }
        else {
            return false;
        }
    }
    else {
        return false;
    }
};

//判断给定的字符串是否包含某一字符串。"hello world".contains("hello1")
String.prototype.contains = function(otherStr){
	 if (typeof(otherStr) === "undefined") {
	     return false;
	 }
	 if (this.indexOf(otherStr.toString()) == -1) {
	     return false;
	 } else {
	     return true;
	 }
};
//判断字符串是否为空。如果为空，则返回false，反之为true, var str="he";
//console.log(str.isNotEmpty());
String.prototype.isNotEmpty = function(){
	if(!this|| this.trim() == ""){
		return false;
	}
	return true;
};
//字符串合并，效率比字符串拼接要快
function StringBuffer(){
	this._stirngs_ = new Array();
}
StringBuffer.prototype.append = function(str){
	this._stirngs_.push(str);
	return this;
};
StringBuffer.prototype.toString = function(){
	return this._stirngs_.join("");
};
StringBuffer.prototype.length = function(){
	return this.toString().length;
};

/**
 * 将数组转换成逗号表达式
 */
Array.prototype.toString = function(){
	if(!this || this.length == 0){
		return "";
	}
	var str = new StringBuffer();
	for(var i=0;i<this.length;i++){
		str.append(this[i]).append(",");
	}
	str = str.toString().substring(0, str.length() - 1);
	return str;
};
//=========================================================================
//日期函数
//=========================================================================
//new Date().getCurDate()，输出当天日期，格式为: 2012-02-24
Date.prototype.getCurDate=function (){
	var myDate = new Date();
	var y =  myDate.getFullYear();
	var m = myDate.getMonth()+1;
	var d = myDate.getDate();
	
	if(m < 10)
		m = "0" + eval(m);
	if(d < 10)
		d = "0" + eval(d);
	return y + "-" + m + "-" + d; 
};

/**
 * 获取星期
 * @param myday
 * @returns
 * new Date().getWeek();
 */
Date.prototype.getWeek = function (){
	var xingqi ;
	switch(this.getDay()) {
		case 0:xingqi="星期日";break;
		case 1:xingqi="星期一";break;
		case 2:xingqi="星期二";break;
		case 3:xingqi="星期三";break;
		case 4:xingqi="星期四";break;
		case 5:xingqi="星期五";break;
		case 6:xingqi="星期六";break;
		default:xingqi="系统错误！";
	}
	return xingqi;
};
//new Date().dateString(new Date())，将给定的日期转换为对应格式，输出当天日期2012年2月24日
Date.prototype.dateString = function (date) {
	return date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
};

//new Date().dateTimeString(new Date()) 获取日期和时间 2012-02-24 13:17:19
Date.prototype.dateTimeString = function(date){
	return date.getCurDate()
	+ " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
};
//=========================================================================
//COOKIE操作工具(zhenglong)
//=========================================================================
var Cookie = {
 /**
  * 设置cookie
  * @param {Object} cname 名称
  * @param {Object} cvalue 值
  * @param {Object} expires 过期时间，可以是数值，表示存活时间，可以是日期对象，表示到期日期
  */
 set: function(cname, cvalue, expires){
     var expiresString;
     if (expires) {
         if (expires instanceof Date) {
             expiresString = ";expires=" + expires.toGMTString();
         } else {
             var d = parseInt(expires);
             if (!isNaN(d)) {
                 var t = new Date();
                 t = new Date(t.getTime() + d);
                 expiresString = ";expires=" + t.toGMTString();
             }
         }
     }
     
     var tmp = cname + "=" + escape(cvalue);
     if (expiresString) {
         tmp += expiresString;
     }
	 tmp += "; path=/";
     document.cookie = tmp;
 },
 
 /**
  * 按名称查找cookie
  * @param {Object} cname
  */
 	find: function(cname){
     	var cookies = document.cookie;
     	var regexpr = new RegExp(cname + "\\s*=[^;]*(;|$)", "g");
     	var ret = cookies.match(regexpr);
     	if (ret) {
         	var val = ret[0].replace(new RegExp(cname + "\\s*="), "").replace(";", "");
         	return unescape(val);
     	}
 	},
 
 	/**
  	* 按名称删除cookie
  	* @param {Object} cname
  	*/
 	remove: function(cname){
     	document.cookie = cname + "=no;expires=" + new Date(1970, 0, 0).toGMTString();
 	}
};

/**
 * 获取给定id下的所有表单元素封装为json对象,如某个div下的所有表单元素
 * @param id
 */
function getWarpPara(id){
	var inputs = $("#" + id).find("input, select");
	var ret = {};
	for (var i = 0; i < inputs.size(); i++) {
		var input = $(inputs[i]);
		//如果是checkbox
		if(input.attr("type") == "checkbox"){
			var curName = input.attr("name").trim();
			var curVal = ret[curName];
			if (curVal === undefined) {//说明还没加入到json对象中
				ret[curName] = [];
			}
			if (input.attr("checked")) {
                 ret[curName].push(input.val().trim());
            }
		} else {
			if(input.val()){
				ret[input.attr("name")] = input.val().trim();
			}else{
				ret[input.attr("name")] = '';
			}
		}
	}
	return ret;
}

/**
 * 根据属性获取对象数组中的对象的值，并组装成属性的数组
 * 用于树上选中的节点，获取属性值，并根据属性组装成数组
 */
function nodeToArray(nodes, property) {
	var nodesArray = new Array();
	if(nodes && nodes.length > 0){
		for(var i=0; i<nodes.length; i++){
			nodesArray.push(nodes[i][property]);
		}
	}
	return nodesArray;
}

/**
 * 合并json数据
 */
function applyIf(target, source){
	for(var key in source){
		target[key] = source[key];
	}
	return target;
}
/**
 * 去除重复json数据
 * 根据json对象的键值，去除重复对象
 * @param target
 * @param source
 */
function jsonUnique(target, source, uniqueKey){
	var dataObject = new Array();
	for(var i=0; i<source.length; i++){
		var flag = true;
		for(var j=0; j<target.length; j++){
			if(target[j][uniqueKey] == source[i][uniqueKey]){
				flag = false;
				break;
			}
		}
		if(flag){
			dataObject.push(source[i]);
		}
	}
	
	for(var k=0; k<dataObject.length; k++){
		target.push(dataObject[k]);
	}
	return target;
}
//=========================================================================
//URL操作工具(zhenglong) BEGIN
//=========================================================================
//获取QueryString的数组
function getQueryParam() {
    var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+", "g"));
    if (result == null) {
        return null;
    }
	var params = {};
    for (var i = 0; i < result.length; i++) {
		var param = result[i].substring(1).split("=");
		params[param[0].trim()] = param[1];
    }
    return params;
}

function getQueryString(){
    var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+", "g"));
    if (result == null)
    {
        return "";
    }
    for (var i = 0; i < result.length; i++)
    {
        result[i] = result[i].substring(1);
    }
    return result;
}

//根据QueryString参数名称获取值
function getQueryStringByName(name){
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1)
    {
        return "";
    }
    return result[1];
}
//根据QueryString参数索引获取值
function getQueryStringByIndex(index){
    if (index == null)
    {
        return "";
    }
    var queryStringList = getQueryString();
    if (index >= queryStringList.length)
    {
        return "";
    }
    var result = queryStringList[index];
    var startIndex = result.indexOf("=") + 1;
    result = result.substring(startIndex);
    return result;
}
/**
 * 在url上设置参数
 * @param {Object} url
 * @param {Object} params 参数对象
 * @param {Object} queryString 参数字符串
 * 
 */
function setQueryString(url, params){
	if(!params){
		return url;
	}
	if(params){
		var paramStr= jQuery.param(params, true);
	   	if(url) {
			if(url.indexOf("?") == -1) {
				url += "?" + paramStr;
			} else {
				url += "&" + paramStr;
			}
		}
	}
	return url;
}
//=========================================================================
//URL操作工具(zhenglong) END
//=========================================================================
/**
 * 弹出窗选择关联对象
 * @param object
 */
function addRelativeToObject(obj){
	for(var s in obj){
		$("#" + s).val(obj[s]);
	}
}

/**
 * 将ID， Pid的json数组组装成Json树
 * @param sNodes
 * @returns {Array}
 */
function assemblyTreeObj(sNodes){
	var r = [];
	var tmpMap = [];
	var key = "id";
	var parentKey="parentId";
	var childsKey = "childs";
	for (i=0, l=sNodes.length; i<l; i++) {
		tmpMap[sNodes[i][key]] = sNodes[i];
	}
	for (i=0, l=sNodes.length; i<l; i++) {
		if (tmpMap[sNodes[i][parentKey]]) {
			if (!tmpMap[sNodes[i][parentKey]][childsKey])
				tmpMap[sNodes[i][parentKey]][childsKey] = [];
			tmpMap[sNodes[i][parentKey]][childsKey].push(sNodes[i]);
		} else {
			r.push(sNodes[i]);
		}
	}

	return r;
}

function dyniframesize(down){
    var pTar = null;
    if (document.getElementById) {
        pTar = document.getElementById(down);
    } else {
        eval('pTar = ' + down + ';');
    }
    if (pTar && !window.opera) {
        //begin resizing iframe 
        pTar.style.display = "block"
        if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight) {
			alert(pTar.contentDocument.body.offsetHeight);
			//ns6 syntax 
			pTar.height = pTar.contentDocument.body.offsetHeight + 20;
			pTar.width = pTar.contentDocument.body.scrollWidth + 20;
		} else {
			if (pTar.Document && pTar.Document.body.scrollHeight) {
				//ie5+ syntax 
				pTar.height = pTar.Document.body.scrollHeight;
				pTar.width = pTar.Document.body.scrollWidth;
			}
		}
    }
	//alert(pTar.height + ":" + pTar.width);
}
//刷新jqgrid，并取消自动刷新
Cookie.set('refresh_grid', 0, null, true);
function refresh_grid() {
	var refersh_grid = Cookie.find('refresh_grid');
	if(refersh_grid == 1) {
		f_reload();
		Cookie.set('refresh_grid', 0);
	}
};
function refersh_page() {
	var refersh_grid = Cookie.find('refersh_page');
	if(refersh_grid == 1) {
		location.reload();
		Cookie.set('refersh_page', 0);
	}
};
/**
 * 将表格设置为自动刷新
 */
function set_grid_auto_refresh(){
	Cookie.set('refresh_grid', 1);
};
/**
 * 将页面设置为自动刷新
 */
function set_page_auto_reload(){
	Cookie.set('refersh_page', 1);
};
/**
 * 重新加载jqgrid数据,默认的id为maingrid
 */
function f_reload(maingrid){
	maingrid = maingrid || "maingrid";
	var manager = $("#" + maingrid).ligerGetGridManager(); 
	manager.loadData(); 
};

function f_save(mainform, parentMenuNo) {
    LG.submitForm(mainform, function (data) {
        var win = parent || window;
        if (data.type && data.type == "ERROR") {  
            win.LG.showError('错误:' + data.content);
        } else { 
            win.LG.showSuccess('保存成功', function () { 
                win.LG.closeAndReloadParent(null, parentMenuNo);
            });
        }
    });
};

function f_saveForDialog(mainform,f_success,f_error){
	LG.submitForm(mainform, function (data) {
		if (data.type && data.type == "ERROR") { 
			console.dir(data); 
	        alert('错误:' + data.content);
	        if(f_error){
	        	f_error();
	        }
	    } else { 
	    	$.ligerDialog.alert("保存成功!",function(){
	    		if(f_success){
	    			f_success();
	    		}
	    		parent.$.ligerDialog.close();
			  	parent.$(".l-dialog,.l-window-mask").remove();
			});
	    }
	});
}

function f_cancel(){
    var win = parent || window;
    win.LG.closeCurrentTab(null);
};

function f_cancelForDialog(showDialog,showText,isCurrent){
	if(showDialog == true){
		$.ligerDialog.alert(showText,function(){
			if(isCurrent){
	    		$.ligerDialog.close();
			  	$(".l-dialog,.l-window-mask").remove();
			}else{
	    		parent.$.ligerDialog.close();
			  	parent.$(".l-dialog,.l-window-mask").remove();
			}
		});
	}else{
		if(isCurrent){
    		$.ligerDialog.close();
		  	$(".l-dialog,.l-window-mask").remove();
		}else{
    		parent.$.ligerDialog.close();
		  	parent.$(".l-dialog,.l-window-mask").remove();
		}
	}
};

function isTabValid(){
	//表单验证
	var $validateForm = $("#validateForm");
	var errorContainer_html = '<div class="validateErrorContainer" id="validateErrorContainer" style="display: none;">' +
		'<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>' +
		'<ul style="display: none;"></ul>' + 
		'</div>';
	$validateForm.before(errorContainer_html);
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	$validateForm.validate({
	    errorContainer: $validateErrorContainer,
	    errorLabelContainer: $validateErrorLabelContainer,
	    wrapper: "li",
	    errorClass: "validateError",
	    ignoreTitle: true,
	    submitHandler: function(form){
	        $(form).find(":submit").attr("disabled", true);
	        form.submit();
	    }
	});
};

/**
 * 身份证号码正则表达式验证
 * @param val
 * @returns
 */
function validLicenceNumber(val){	
	var reg=/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
	return reg.test(val);
}

/**
 * 手机号码正则表达式验证
 * @param val
 * @returns
 */
function validPhoneNumber(val){	
	var reg = /^1\d{10}$/;
	return reg.test(val);
}

/**
 * 验证码正则表达式验证
 * @param val
 * @returns
 */
function validCode(val){	
	var reg = /^\d{4}$/;
	return reg.test(val);
}