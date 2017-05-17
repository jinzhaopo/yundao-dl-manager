var MemberPrice={
	priceBox:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	dialog: undefined,
	init:function(){
		var self = this;
		self.bindMbPricBtnEvent();
	},
	bindMbPricBtnEvent:function(){
		var self = this;
		$(".mempriceBtn").unbind("click").bind("click",function(){
			self.priceBox = $(this).next(".member_price_box"); //当前的会员价box
			var price = $(this).siblings(".prices").val(); // 获取价格，规则为当前操作按钮的
			if( parseFloat(price)!= price) {
				alert("销售价必须是数字!");
				return false;
			}else{
				self.dialog = $.ligerDialog.open({ url: base + '/admin/memberPrice/list.jhtml?price=' + price, 
					height: 300, width: 500, 
					title: '编写会员价格',
					load: true,
					allowClose : false
				});
			}
		});
	},
	
	//初始化弹出对话框，由pricebox计算lvprice
	initDlg:function(){
		var self = this;
		//var editor = $("#levelPriceEditor");
	
		this.priceBox.find(".lvPrice").each(function(){//由.lvPrice找到 价格输入框
			var lvid = $(this).attr("lvid");
			$("#levelPriceEditor input.lvPrice[lvid='"+lvid+"']").val(this.value);
		});
		$("#mbPriceBtn").click(function(){
			self.createPriceItems();
		}); 
		
		$("#mbPriceCloseBtn").click(function(){
			self.dialog.close();
		});
	},
	//由对话框的input 创建会员价格表单项
	createPriceItems: function(){
		var member_price_box = this.priceBox;
		member_price_box.empty();
		var index = member_price_box.attr("index");
		$("#levelPriceEditor>tbody>tr").each(function(){
			var tr = $(this);
			var lvid = tr.find(".lvid").val();
			var lvPrice = tr.find(".lvPrice").val();
			//如果开启规格，货品生成会员价时要按顺序指定name_i，为了后台读取每个货品的会员价方便
			//没开启规格，生成此商品的会员价不需要指index，即name直接为lvid,lvPric即可
			if(index){
				member_price_box.append("<input type='hidden' class='lvid' name='lvid_"+index+"' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice_"+index+"' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}else{
				member_price_box.append("<input type='hidden' class='lvid' name='lvid' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}
		}); 
		this.dialog.close();
	}
};