var isValidCellPhone = false;
var isValidPassword = false;
var isValidImageCode = true;
$(function(){
	$("#cellPhone").blur(function(){
		if($(this).val().length > 0) {
			cellPhoneValidation();
			$(".login-error").text("");
		}
	});
	
	$("#password").blur(function(){
		if($(this).val().length > 0) {
			passwordValidation();
			$(".login-error").text("");
		}
	});
	
	var failedTimes = 0;
	$("#imageCode").blur(function(){
		if(failedTimes >= 3) {
			isValidImageCode = false;
			if($(this).val().length > 0) {
				imageCodeValidation();
				$(".login-error").text("");
			}
		} 
	});
	
	$("#loginImageCode").attr("src",path + "/login?process=generateImageCode&sda=" + new Date().getTime());
	$("#loginImageCode").click(function(){
		$("#loginImageCode").attr("src",path + "/login?process=generateImageCode&sda=" + new Date().getTime());
	});
	
	$(".cookie p").click(function(){
		if($(".checkbox i").is(":hidden")){
			$(".checkbox i").show();
			$("#cookieHidden").val("on");
		}else{
			$(".checkbox i").hide();
			$("#cookieHidden").val("off");
		}
	});

	// Submit
	$("#loginBTN").click(function() {
		cellPhoneValidation();
		passwordValidation();
		if(failedTimes >= 3) {
			imageCodeValidation();
		}
		var valid = isValidCellPhone && isValidPassword && isValidImageCode;
		if(valid) {
			$("#loginBTN").val("登录中...");
			$("#loginBTN").attr("disabled", true);
			$.ajax({
				url: path+"/login",
				data: {
					process: "login_a",
					cellPhone: $("#cellPhone").val(),
					password: $("#password").val(),
					imageCode: $("#imageCode").val(),
					UZWorkToken: $("#UZWorkToken").val(),
					cookie: $("#cookieHidden").val()
				},
				dataType: "JSON",
				type: "POST",
				error: function(){UZDialogFade("请求失败");},
				success: function(data){
					if(data.success){
						if(getCookie("backUrl") && getCookie("backUrl") != ''){
							location = getCookie("backUrl");
							addCookie("backUrl",'');
						}
						else{
							location= path;
						}
					}
					else {
						$("#loginBTN").val("登录");
						$("#loginBTN").attr("disabled",false);
						$("#password").val('');
						$(".login-error").text(data.message).show();
						failedTimes = data.value;
						if(failedTimes >= 3) {
							$(".info .code").show();
							// Refresh image code
							$("#loginImageCode").attr("src",path + "/login?process=generateImageCode&sda=" + new Date().getTime());
//							$(".info #imageCode").focus();
							isValidCellPhone = false;
						}
					}
				},
				complete:function(){
				}
			});
		}
		else {
			return false;
		}
	});
});

function cellPhoneValidation() {
	var t = $("#cellPhone");
	var val = t.val();
	if(iscellPhone(val)){
		isValidCellPhone = true;
	}
	else {
		t.errorShow("请输入已注册手机号");
		isValidCellPhone = false;
	}
}

function passwordValidation() {
	var t = $("#password");
	var val = t.val();
	if(val != "" && val.length >= 6 && val.length <= 20){
		isValidPassword = true;
	}
	else {
		t.errorShow("请输入正确的登录密码");
		isValidPassword = false;
	}
}

function imageCodeValidation() {
	var t = $("#imageCode");
	var val = t.val();
	if(val != "" && val.length == 4){
		$.ajax({
			url: path + "/login",
			data: {process: "imageCode", imageCode: val},
			dataType: "JSON",
			type: "POST",
			async: false,
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				if(data.success) {
					isValidImageCode = true;
				}
				else {
					t.errorShow(data.message);
					isValidImageCode = false;
				}
			}
		});
	}
	else {
		t.errorShow("请输入图片验证码");
		isValidImageCode = false;
	}
}