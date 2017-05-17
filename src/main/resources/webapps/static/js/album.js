$(function(){
	// 商品图片预览滚动栏
	$(".productImageArea .scrollable").scrollable({
		speed: 600
	});
	
	// 显示商品图片预览操作层
	$(".productImageArea li").livequery("mouseover", function() {
		$(this).find(".productImageOperate").show();
	});
	
	// 隐藏商品图片预览操作层
	$(".productImageArea li").livequery("mouseout", function() {
		$(this).find(".productImageOperate").hide();
	});
	
	// 商品图片左移
	$(".left").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImagePrevLi = $productImageLi.prev("li");//上一个li标签
		var $productImagePrevLis = $productImageLi.prevAll("li");//上一个li标签
		if ($productImagePrevLis.size() > 1) {
			$productImagePrevLi.insertAfter($productImageLi);
		}else if($productImagePrevLis.size() == 1){
			alert("主图片无法移动");
		}
	});
	
	// 商品图片右移
	$(".right").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImageNextLi = $productImageLi.next("li");
		if ($productImageNextLi.length > 0) {
			$productImageNextLi.insertBefore($productImageLi);
		}
	});
	
	// 商品图片删除
	$(".productImageBox .delete").livequery("click", function() {
		if(!confirm("确定删除相册吗?")){
			return false;
		}
		var $productImageLi = $(this).parent().parent().parent();
		//如果是主图片图片
		if($productImageLi.prevAll().size() == 0){
			var $productImagePreview = $productImageLi.find(".productImagePreview");
			var image = $productImageLi.find("input[name='image_default'], input[name='image_files']");
			image.val("");
			$productImagePreview.html("主图片");
			return;
		}
		if ($productImageLi.nextAll().size() > 0) {
			$productImageLi.remove();
		}
		
	});
	
	// 商品图片选择预览
	var $productImageScrollable = $(".productImageArea .scrollable").scrollable();
	var productImageLiHtml = '<li><div class="productImageBox"><div class="productImagePreview">暂无图片</div><div class="productImageOperate">' +
		'<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a><a class="right" href="javascript: void(0);" title="右移" hidefocus="true">' +
		'</a><a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a></div>' +
		'<a class="productImageUploadButton" href="javascript: void(0);">' +
		'<input type="hidden" name="image_files">' +
		'<input type="button" class="browserButton" hidefocus="true" />' +
		'<div>上传新图片</div></a></div></li>';
	
	var browserOption = {
		"createThumb": 1,//创建缩略图
		"callback": function(url, store_path){
			var $productImageLi = $(this).parent().parent().parent();
			var $productImagePreview = $productImageLi.find(".productImagePreview");
			var $productImageUploadButton = $productImageLi.find(".productImageUploadButton");
			$productImagePreview.html("<img src='" + url + "'/>");
			$productImageUploadButton.find("input:first").val(store_path);
			if ($productImageLi.next().length == 0) {
				$productImageLi.after(productImageLiHtml);
				$productImageLi.next().find(".browserButton").browser(browserOption);
				if ($productImageScrollable.getSize() > 5) {
					$productImageScrollable.next();
				}
			}
		}
	};
	
	$(".browserButton").browser(browserOption);
	
});