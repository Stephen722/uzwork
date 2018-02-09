var isValidCellPhone = false;
var isValidImageCode = false;
var isValidSMSCode = false;
var isValidPassword = false;
var checked = true;
$(function(){
	$("#reg-imageCode").attr("src",path + "/register?process=generateImageCode&sda=" + new Date().getTime());
	$("#reg-imageCode").click(function(){
		$("#reg-imageCode").attr("src",path + "/register?process=generateImageCode&sda=" + new Date().getTime());
	});

	$("#cellPhone").blur(function(){
		if($(this).val().length > 0) {
			cellPhoneValidation();
			$("#reg-error").text("");
		}
	});
	
	$("#imageCode").blur(function(){
		if($(this).val().length > 0) {
			imageCodeValidation();
			$("#reg-error").text("");
		}
	});
	
	$("#SMSCode").blur(function(){
		if($(this).val().length > 0) {
			SMSCodeValidation();
			$("#reg-error").text("");
		}
	});
	
	$("#password").blur(function(){
		if($(this).val().length > 0) {
			passwordValidation();
			$("#reg-error").text("");
		}
	});
	
	// Submit
	$("#regBTN").click(function() {
		cellPhoneValidation();
		imageCodeValidation();
		SMSCodeValidation();
		passwordValidation();
		if(!checked) {
			$(".agree .error").text("请接受UZWORK用户协议");
		}
		var valid = isValidCellPhone && isValidImageCode && isValidSMSCode && isValidPassword && checked;
		if(valid) {
			$("#regBTN").val("提交中...");
			$("#regBTN").attr("disabled", true);
			$.ajax({
				url:path+"/register",
				data:{
					process: "post",
					cellPhone: $("#cellPhone").val(),
					imageCode: $("#imageCode").val(),
					SMSCode: $("#SMSCode").val(),
					password: trimStr($("#password").val()),
					UZWorkToken: $("#UZWorkToken").val()
				},
				dataType:"JSON",
				type:"POST",
				error: function(){UZDialogBG("请求失败");},
				success: function(data){
					if(data.success){
						$("#regForm").submit();
					}
					else{
						$("#regBTN").val("注册");
						$("#regBTN").attr("disabled", false);
						
						$("#reg-error").text(data.message).show();
						// Refresh image code
						$("#reg-imageCode").attr("src",path + "/register?process=generateImageCode&sda=" + new Date().getTime());
						return false;
					}
				}
			});
		}
		else {
			return false;
		}
	});
	
	$("#reg .right .agree .ck").click(function(){
		checkedValidation();
	});
	
	var num=60
	$("#Btn").click(function()
	{
		var loop = setInterval(function(){
			if(num==0){
				clearInterval(loop);
				$("#Btn").removeAttr("disabled").val("发送验证码");
				num=60;
				return;
			}else{
				$("#Btn").attr("disabled",true).val("重新发送" + num );
				num--;
			}
		},1000)
	});
});

function aJaxValidation(process, t, parameter) {
	$.ajax({
		url: path + "/register",
		data: parameter,
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogBG("请求失败");},
		success: function(data){
			if(data.success){
				t.addClass("icon-correct");
				setValid(process, true);
			}
			else{
				t.removeClass("icon-correct");
				t.errorShow(data.message);
				setValid(process, false);
			}
		}
	});
}

function setValid(process, valid) {
	if("cellPhone" == process) {
		isValidCellPhone = valid;
	}
	else if("imageCode" == process) {
		isValidImageCode = valid;
	}
	else if("SMSCode" == process) {
		isValidSMSCode = valid;
	}
}
function cellPhoneValidation() {
	var t = $("#cellPhone");
	var val = t.val();
	if(iscellPhone(val)){
		aJaxValidation("cellPhone", t, {process: "cellPhone", cellPhone: val});
	}
	else {
		t.removeClass("icon-correct");
		t.errorShow("请输入正确的手机号");
		isValidCellPhone = false;
	}
}

function imageCodeValidation() {
	var t = $("#imageCode");
	var val = t.val();
	if(val != "" && val.length == 4){
		aJaxValidation("imageCode", t, {process: "imageCode", imageCode: val});
	}
	else {
		t.errorShow("请输入正确的图片验证码");
		isValidImageCode = false;
	}
}

function SMSCodeValidation() {
	var t = $("#SMSCode");
	var val = t.val();
	if(val != "" && val.length == 4){
		aJaxValidation("SMSCode", t, {process: "SMSCode", SMSCode: val});
	}
	else {
		t.errorShow("请输入正确的短信验证码");
		isValidSMSCode = false;
	}
}

function passwordValidation() {
	var t = $("#password");
	var val = t.val();
	if(val != "" && val.length >= 6 && val.length <= 20){
		t.addClass("icon-correct");
		isValidPassword = true;
	}
	else {
		t.errorShow("请输入6-20位密码");
		isValidPassword = false;
	}
}

function checkedValidation() {
	if($("#reg .right .agree .checkbox i").is(":hidden")) {
		checked = true;
		$(".checkbox i").show();
		$(".agree .error").text("");
	}
	else {
		checked = false;
		$(".checkbox i").hide();
	}
}