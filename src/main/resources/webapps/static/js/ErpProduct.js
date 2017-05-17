var ErpProduct={
	erpInput:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	erpInputBtn: undefined,
	dialog: undefined,
	init:function(){
		var self = this;
		self.bindErpProductBtnEvent();
	},
	bindErpProductBtnEvent:function(){
		var self = this;
		$(".erpProductBtn").unbind("click").bind("click",function(){
			self.erpInputBtn = $(this);
			self.erpInput = $(this).next(".erp_product_id"); //当前的ERP隐藏域
			self.dialog = $.ligerDialog.open({ 
				url: base + '/admin/product/erplist.jhtml', 
				height: 800, width: 1050, 
				title: "绑定ERP货品"
			});
		});
	},
	/**
	 * 填充ERP的ID
	 * @param {Object} record //选中的记录
	 */
	fillErpID: function(record){
		var self = this;
		self.erpInput.val(record.id);
		if(self.erpInputBtn.hasClass("spec")){
			var tr = self.erpInputBtn.parent().parent();
			tr.find("input[name='sns']").val(record.productSn);
			tr.find("input[name='prices']").val(record.price);
			tr.find("input[name='marketPrices']").val(record.marketPrice);
			tr.find("input[name='weights']").val(record.weight);
			tr.find("input[name='stores']").val(record.store);
		}else{
			self.erpInputBtn.val(record.id);
		}
		self.dialog.close();
	}
};