//类别、类型、品牌联动
var TypeCat={
	init: function(){
		var self  = this;
		var type_id = $("#type_id").val();
		var brand_id = $("#brand_value").val();
		this.loadBrandsInput(type_id, brand_id);
		//this.loadPropsInput(type_id);
		//this.loadParamsInput(type_id);
	},
	//异步读取品牌输入项
	loadBrandsInput: function(type_id, brand_id){
		$.ajax({
			type: "get",
			url: base + "/admin/goodsType/listBrand.jhtml",
			data:"type_id=" + type_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
			   $("#brand_id").empty().append(result);
			   if(brand_id){
				   $("#brand_id").val(brand_id);
			   }
			}
		});
	},
	//加载参数输入项
	loadParamsInput: function(type_id){
		$.ajax({
			type: "get",
			url: base + "/admin/goodsType/disParamsInput.jhtml",
			data: "type_id=" + type_id + "&m=" + new Date().getTime(),
			dataType: "html",
			success: function(result){
				try{
					$("#tab_params").html(result);
			    }catch(e){
			    	alert(e); 
			    }
			}
		});
	},
	loadPropsInput: function(type_id){
		$.ajax({
			type: "get",
			url: base + "/admin/goodsType/disPropsInput.jhtml",
			data:"type_id=" + type_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				$("#tab_props").append(result);
			//   $("form.validate").addinput($("#tab_props input,#tab_props select"));
				//var catid = $("#cat_id").val();
			 
//				if(catid =='3' || catid =='4' || catid =='12' || catid =='19' ){}else {
//					$("#weight_tr").hide();
//				}
			}
		});
	}
};
$(function(){
	TypeCat.init();
});