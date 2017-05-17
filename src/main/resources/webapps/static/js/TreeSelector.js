//创建TreeSelector对象
function TreeSelector(item) {
	this._data = new Array();
	this._item = document.getElementById(item);
	this._rootId = -1;// 规定根节点-1
	this.headerKey = "";
	this.headerValue = undefined; // select的第一个值，如果为undefined为不显示，否则根据给定值显示
	this.defaultValue = undefined; // select框的默认值
	this.isDisabledParent = undefined; // 是否禁用掉父节点
}

// 增加一个节点
TreeSelector.prototype.add = function(_id, _pid, _text, _disabled) {
	this._data[this._data.length] = {
		id : _id,
		pid : _pid,
		text : _text,
		disabled : _disabled
	};
}

//获取根节点
TreeSelector.prototype.getRoot = function() {
	var len = this._data.length;
	var root = new Array();
	for ( var i = 0; i < len; i++) {
		if(!this._data[i].pid || this._data[i].pid == this._rootId){
			root.push(this._data[i]);
		}
	}
	return root;
}

// 创建树，只需循环根节点
TreeSelector.prototype.createTree = function() {
	//判断默认值是否存在
	if(this.headerValue){
		var option = new Option(this.headerValue, this.headerKey);
		this._item.add(option);
	}
	
	var rootNode = this.getRoot();
	var len = rootNode.length;
	var isEndList = new Array();
	isEndList.push(true);
	for ( var i = 0; i < len; i++) {
		this.createSubOption(0, rootNode[i], isEndList);
	}	
}

// 创建子节点
TreeSelector.prototype.createSubOption = function(level, current, isEndList) {
	var blank = "";
	if(level >= 1){
		blank = "　";
	} 
	
	// 线条列
	for (var i = 1; i < level; i++) {
		if (!isEndList[i]) {
			blank += "│";
		} else {
			blank += "　";
		}
	}
	
	// 节点列
	if (level == 0) {
		// 父节点
		blank += "♀";
	} else if (isEndList[level]) {
		blank += "└";
	} else {
		blank += "├";
	}

	var cld = this.getTreeChild(current);
	
	var option = new Option(blank + current.text, current.id);
	//如果定义了disabled选项，并且disabled为'true'，则将option设置为disabled状态
	if(this.isDisabledParent && this.isDisabledParent){
		if(cld && cld.length > 0){
			option.disabled=true;
		}
	}
	//设置默认属性
	if(this.defaultValue && this.defaultValue == current.id){
		option.selected = true;
	}
	
	this._item.options.add(option);// 添加Option选项
	
	//获取子节点
	for ( var j = 0; j < cld.length; j++) {
		isEndList.push(j == cld.length - 1);
		this.createSubOption(level + 1, cld[j], isEndList);//寻找子节点
		isEndList.pop();
	}

}

// 根据给定的节点获取子节点
TreeSelector.prototype.getTreeChild = function(current){
	var	treeChild = new Array();
	for(var i=0; i<this._data.length; i++){
		if(this._data[i].pid == current.id){
			treeChild.push(this._data[i]);
		}
	}
	return treeChild;
}

// 获取第一个disable为false的数据
TreeSelector.prototype.getFirstUndisabledIndex = function(){
	for(var i=0; i<this._data.length; i++){
		if(!this._data[i].disabled || this._data[i].disabled == 'false'){
			return i;
		}
	}
}

/**
 * 解决ie bug
 */
function fixIEBug(TreeSelector){
    if (document.getElementsByTagName) {   
        var s = document.getElementsByTagName("select");   
        if (s.length > 0) {
            window.select_current = new Array();   
  
            for (var i=0;i<s.length; i++) {
				var oSelect = s[i];
				// 当焦点在select选项框上
				oSelect.onfocus = function(){
                	window.select_current[this.id] = TreeSelector.getFirstUndisabledIndex();
				}
				oSelect.onchange = function(){
              		restore(this, TreeSelector);
				}
				emulate(oSelect);   
            }   
        }
    }
}   

function restore(e, TreeSelector) {
    if (e.options[e.selectedIndex].disabled) {
        e.selectedIndex = TreeSelector.getFirstUndisabledIndex();
    }
}

function emulate(e) {
    for (var i=0;i<e.options.length; i++) {  
    	var option = e.options[i];
    	if (option.disabled) {
    		option.style.color = "graytext";
    	} else {   
      	option.style.color = "menutext";
      }   
    }   
}