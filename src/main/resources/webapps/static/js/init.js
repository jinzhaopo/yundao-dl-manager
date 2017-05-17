$(function(){
	// 表单验证
	var form = $("form.validate");
	//如果不存在tab标签
	if (form && form.size() > 0) {
		var validate = $("form.validate").validate({
			errorClass: "validateError",
			ignore: ".ignoreValidate",
			errorPlacement: function(error, element){
				if (!element.attr("id")) 
					element.attr("id", new Date().getTime());
				if (element.hasClass("validateError")) {
					element.addClass("l-text-field");
				}
				$(element).removeAttr("title").ligerHideTip();
				$(element).attr("title", error.html()).ligerTip({
					distanceX: 5,
					distanceY: -3,
					auto: true
				});
			//			var messagePosition = element.metadata().messagePosition;
			//			if("undefined" != typeof messagePosition && messagePosition != "") {
			//				var $messagePosition = $(messagePosition);
			//				if ($messagePosition.size() > 0) {
			//					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
			//				} else {
			//					error.insertAfter(element).fadeOut(300).fadeIn(300);
			//				}
			//			} else {
			//				if(element.is(":radio")){
			//			        error.appendTo(element.parent().next().next() );
			//				}else if(element.is(":checkbox")){
			//			        error.appendTo(element.next());
			//			    }else{
			//					$(element).removeAttr("title").ligerHideTip();
			//			    	error.insertAfter(element).fadeOut(300).fadeIn(300);
			//			    }
			//			}
			},
			success: function(lable){
				if (!lable.attr("for")) 
					return;
				var element = $("#" + lable.attr("for"));
				
				if (element.hasClass("l-text-field")) {
					element.removeClass("l-text-field");
				}
				$(element).removeAttr("title").ligerHideTip();
			},
			submitHandler: function(form){
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		});
	};
	
	//时间日期控件
	$("input.WdatePicker").focus(function(){
		var data = $(this).metadata();
		var datePickerOptions = {
			dateFmt: data.dateFmt == undefined ? "yyyy-MM-dd" : data.dateFmt,
			minDate: data.minDate == undefined ? "" : data.minDate,
			maxDate: data.maxDate == undefined ? "" :data.maxDate
		};
		new WdatePicker(datePickerOptions);
	});
	
	//所有必填项带上星号
	$("form input, form select").each(function(){
		if($(this).metadata().required == true){
			 $(this).addClass("require");
		}
	});
	
	$("form select").each(function(){
		if($(this).metadata().required == true){
			 $(this).after("&nbsp;&nbsp;<span class='red'>*</span>");
		}
	});
});