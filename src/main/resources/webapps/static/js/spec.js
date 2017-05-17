var ligerDialog;
$(function(){
	Product.init();
	//是否低佣金商品点击事件
//	$("input[name='goods.isExchange']").click(function(){
//		if($(this).val() == "true"){
//			$("#dyj_tr").show();
//			$("#dyj").removeAttr("disabled");
//			$("#productTable input[name='dyjs']").removeAttr("disabled");
//		}else{
//			$("#dyj_tr").hide();
//			$("#dyj").attr("disabled", true);
//			$("#productTable input[name='dyjs']").attr("disabled", true);
//		}
//	}).trigger("click");
	//是否启用规格点击事件
	var haveSpec = $("#haveSpec").val();
	if(haveSpec == "true"){
		$("#no-goods-spec input").attr("disabled", true);
		$("#productTable input[name=dyjs]").attr("disabled", true);
	}
	$(".isMarketablesBox").click(function(){
		if($("input[name=isMarketable]:checked").val() != "true" && $(this).attr("checked")){
			alert("商品还处于下架状态,请先上架商品后再上架货品!");
			$(this).removeAttr("checked");
			return;
		}
		if($(this).attr("checked") == 'checked'){
			$(this).siblings().val("true");
		}else{
			$(this).siblings().val("false");
		}
		
	});
	MemberPrice.init();
});

//关闭规格
function closeSpec(){
	if(!confirm("关闭后现有已添加的货品数据将全部丢失,确定要关闭规格吗?")){
		return;
	}
	//商品货品tr
	$("#productTable tbody").empty();
	$("#productTable #priceTh").prevAll("th").not(":last").remove();
	$("#goods-spec").hide();
	$("#no-goods-spec").show();
	$("#no-goods-spec input").attr("disabled", false);
	$("#haveSpec").val("false");
}

function putaway(){
	$(".isMarketablesBox").each(function(i,item){
		item.checked = true;
	});
	$("[name='isMarketables']").each(function(i,item){
		item.value = true;
	});
}

function soldout(){
	$(".isMarketablesBox").each(function(i,item){
		item.checked = false;
	});
	$("[name='isMarketables']").each(function(i,item){
		item.value = false;
	});
}

var Product= {
	init: function(){
		var self=this;
		$("#specOpenBtn").click(function(){
			Product.openSpecDialog();
		});
		$("#closeSpec").click(function(){
			closeSpec();
		});
		$("#putaway").click(function(){
			putaway();
		});
		$("#soldout").click(function(){
			soldout();
		});
		$("#addProductButton").click(function(){
			self.addProduct();
		});
		//编辑时货品的删除事件
		$("#productTable .delete").live("click", function(){
			self.deleteProRow($(this));
		});
		
		MemberPrice.bindMbPricBtnEvent();
	},
	//打开选择规格的窗口
	openSpecDialog:function(){
		ligerDialog = $.ligerDialog.open({ width:500, height:450, url: 'spec_select.jhtml', title: "规格"});
	},
	
	createPros:function(specs,names){
		//隐藏销售价、市场价、货号、重量、库存等信息
		$("#no-goods-spec").hide();
		$("#no-goods-spec input").attr("disabled", true);
		$("#goods-spec").show();
		$("#haveSpec").val("true");
		/** 
		 * 获取会员价,成功后生成货品grid
		 */
		var self = this;
		$.ajax({
			url: base + '/admin/memberPrice/disLvInput.jhtml',
			dataType:'html',
			success:function(html){
				self.doCreatePros(specs, names, html);
			},
			error:function(){
				alert("获取会员价出错");
			}
		});
		
	},
	doCreatePros:function(specs, names, memPriceInput){
		var price = $("#price").val();
		var weight = $("#weight").val();
		var marketPrice = $("#marketPrice").val();
//		var dyj = $("#dyj").val();
		//是否抵用金兑换
//		var isExchange = $("input[name='goods.isExchange']:checked").val();
		
		var self =this;
		for(i in names){
			$("#priceTh").before("<th>"+names[i]+"</th>");
		}
		var products = combinationAr(specs);
		for(i in products){
			//每个规格的会员价名称为顺序排列，如第一个货品的会员价名称为：lvid_0和lvPrice_0
			var priceInputHtml = memPriceInput.replace(/name="lvid"/g, 'name="lvid_' + i + '"');
			priceInputHtml = priceInputHtml.replace(/name="lvPrice"/g, 'name="lvPrice_' + i + '"');
			var specar = products[i];
			var html ='<tr><td><input type="text" name="sns" class="text" style="width:125px" autocomplete="off"><input type="hidden" name="productids"></td>';
			var specvids="";
			var specids ="";
			for(j in specar){
				
				var spec = specar[j];
				if(j != 0){
					specvids+=",";
					specids+=",";
				}
				specvids += spec.specvid;
				specids += spec.specid;
				html += "<td>" ;
				if( spec.spectype == "text"){
					html += "<div class='select-spec-unselect spec_selected'><span>" + spec.specvalue +"</span></div>";
					html += "<input type=\"hidden\" name=\"spectype_"+i+"\" value='text'/>";
					html += "<input type=\"hidden\" name=\"specimage_"+i+"\" value='" + spec.specimage + "' />";
				}else{
					var img = '<img height="20" width="20" title="'+spec.specvalue+'" alt="'+spec.specvalue+'" src="'+spec.specimage+'">';
				//	img = img.replace(Eop.ContextPath+"/plugin/spec/","");
					html += '<div class="select-spec-unselect spec_selected">'+img+'</div>';
					html += "<input type=\"hidden\" name=\"spectype_"+i+"\" value='image' />";
					html += "<input type=\"hidden\" name=\"specimage_"+i+"\" value='" + spec.specimage + "' />";
				}
				html += "<input type=\"hidden\" name=\"specvalue_"+i+"\" value=\""+spec.specvalue+"\" />";
				html += "</td>";
			}

			//html+="<td>";
		
			//html+='<input type="text" name="stores" size="4" autocomplete="off"  value="'+ store +'"> </td>';
			html+="<td><input type='hidden' name='specids' value='"+ specids +"' />";
			html+="<input type='hidden' name='specvids' value='"+specvids+"' />";	
			html+='<input type="text" onblur="changeProductMarketPrice(this);" class="prices text" name="prices" size="8" value="' + price + '">' +
					'<input type="button" class="mempriceBtn" value="会员价" />' +
					'<div class="member_price_box" index="' + i + '">' + priceInputHtml + '</div></td>';
			//html+='<td><input type="text" name="costs" size="8" autocomplete="off" value="'+cost+'"></td>';
			html+='<td><input type="text" name="marketPrices" size="8" class="text" autocomplete="off" value="' + marketPrice + '"></td>';
			//html+='<td><input type="text" name="dyjs" size="8" class="text" autocomplete="off" value="' + dyj + '"></td>';
			html+='<td><input type="text" name="weights" size="4" class="text" autocomplete="off" value="' + weight + '"></td>';
			html+='<td><input type="text" name="stores" size="4" class="text" autocomplete="off"></td>';
			html+='<td><input type="checkbox" class="isMarketablesBox"/><input type="hidden" name="isMarketables"/></td>';
			html+='<td><a href="javascript:;" ><img class="delete" src="' + base + '/resource/images/transparent.gif" productid="0"></a>';
			html+="</td></tr>";
			var trEl = $(html);
//			if(isExchange == "false"){
//				trEl.find("input[name='dyjs']").attr("disabled", true);
//			}
			
			$("#productTable").append(trEl);
			trEl.find("img.delete").click(function(){
				self.deleteProRow($(this));
			});
			trEl.find(".isMarketablesBox").click(function(){
				$(this).siblings().val($(this).attr("checked"));
			});
			MemberPrice.init();
		}
	},
	//获取所有的规格值
	getAllSpecValue: function(spec_id){
		
	},
	//增加货品
	addProduct: function(){
		var clone = $("#productTable tbody").find("tr:last").clone(); 
		clone.appendTo($("#productTable"));
		
//		var specids_val = clone.find("input[name]").val();
//		if(specids_val.trim() == ""){
//			return;
//		}
//		var specids = specids_val.split(",");
//		for(var i=0; i<specids.length; i++){
//			$.ajax({
//				url: base + '/admin/goods/specification!specvalues.action',
//				data: {"specification.id": specids[i]},
//				success:function(result){
//					var valueList = result.valueList;
//					for(var j=0;j<valueList.length; j++){
//						
//					}
//				}
//			});
//		}
	},
	
	deleteProRow: function(link){
		
		if(!confirm("删除后货品数据将不能恢复，确认删除本行?")){
			return;
		}
		link.parents("tr").remove();
	}
};
/**
* 将一个值放在一个数组未尾，形成新的数据
*/
function putAr(ar1,obj){
	var newar =[];
	for(var i=0;i<ar1.length;i++){
	    	newar[i] =ar1[i];
	}
	newar[ar1.length] = obj;
	return newar;
};

/*
 *
 * 组合两个数组
 * 如果第一个数组是二维数组，则调用putAr分别组合
 * 如果第一个数组是一维数组，则直接和ar2组合
 */
function combination(ar1, ar2){
	var ar = new Array();
	var k=0;
	if(!ar2) { //数组只有一唯的情况
		for(var i=0;i<ar1.length;i++){
			ar[k] = [ar1[i]];
			k++;
		}	
		return ar;
	}
 
	for(var i=0; i<ar1.length; i++){
		for(var j=0;j<ar2.length;j++){
			if(ar1[i].constructor == Array){
				ar[k]= putAr(ar1[i], ar2[j]);
			}else{
				ar[k] = [ar1[i], ar2[j]];
			}	 
			k++;
		}
	}
	return  ar;
};

function combinationAr(spec_ar){
	var ar;
	var m =0 ;

	if(spec_ar.length==1){ 
		return combination(spec_ar[0]);
	}

	while(m < spec_ar.length - 1){
		if(m==0){
			ar = spec_ar[0];
		}
		ar = combination(ar,spec_ar[m+1]);
		m++;
	};
	return ar;
};
/**
 * wangzhongxing
 * 2013-11-30
 */
function changeProductMarketPrice(target) {
	var price = target.value;
	if(!window.isNaN(price)) {
		var marketPrice = new Number(1.2 * price);
		$(target).parent("td").next("td").children("input").first().val(marketPrice.toFixed(2));
	}
};