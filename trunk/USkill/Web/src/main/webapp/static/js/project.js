$(function(){
	$("#sider .fav").click(function(){
		if(isLogin()) {
			favorite();
		}
		else {
			UZDialogFade("请先登录");
		}
	});
	
	var status = $("#detail .info em").attr("id");
	if ("RECRUIT" == status) {
		$("#sider #bid").click(function(){
			if(isLogin()) {
				if("QUALIFIED" == $("#myStatus").val()) {
					if($("#myProject").val() == true) {
						UZDialogFade("你不能投标自己发布的项目");
					}
					else {
						bidConfirm();
					}
				}
				else {
					UZDialogFade("你还没有认证，不能投标");
				}
			}
			else {
				UZDialogFade("请先登录");
			}
		});
	}
	else {
		$("#sider #bid").val("投标期已过");
	}
});
function isLogin() {
	var login = false;
	if($("#head .right .me dd").length > 0) {
		login = true;
	}
	return login;
}
function favorite() {
	var projectId = $("#projectId").val();
	$.ajax({
		url: path + "/p",
		data: {process: "favorite", projectIdStr: projectId},
		dataType: "JSON",
		type: "POST",
		async: true,
		success: function(data){
			UZDialogFade(data.message);
		},
		error: function(){
			UZDialogFade("请求失败");
		},
		beforeSend: function () {
			$("#sider .fav").attr("disabled", true);
	    },
		complete:function(){
			$("#sider .fav").attr("disabled", false);
		}
	});
}
function bidConfirm() {
	$("#project-confirm").UZDialog();
	$("#price").blur(function(){
		var t = $(this);
		var value = t.val();
		if(value == "") {
			t.errorShow("请输入您的报价");
		}
		else if(!isInt(value)){
			t.errorShow("请输入整数报价");
		}
	});
	$("#proposal").blur(function(){
		var t = $(this);
		var value = t.val();
		var len = value.length;
		if(len > 0 && (len < 5 || len > 200)) {
			t.errorShow("请输入5-200字的建议");
		}
	});
	
	$("#post").click(function(e){
		if(isLogin()) {
			var valid = true;
			var price = $("#price").val();
			var proposal = $("#proposal").val();
			if(price == "") {
				 $("#price").errorShow("请输入您的报价");
				 valid = false;
			}
			else if(!isInt(price)){
				 $("#price").errorShow("请输入整数报价");
				 valid = false;
			}
			else {
				var len = proposal.length;
				if(len > 0 && (len < 5 || len > 200)) {
					t.errorShow("请输入5-200字的建议");
					valid = false;
				}
			}
			if(valid) {
				bidPost();
			}
			else {
				return valid;
			}
		}
		else {
			UZDialogFade("请先登录");
		}
	});
}
function bidReset() {
	$("#price").val("");
	$("#proposal").val("");
	$("#project-confirm .error-msg").each(function(){
		$(this).text("").hide();
		var cls=$(this).attr("class");
		if($(this).siblings(".input-uz")) {
			$(this).siblings(".input-uz").removeClass("error-bd");
		}
	});
	$("#project-confirm").hide();
	UZDialogClose();
}
function bidPost() {
	var projectId = $("#projectId").val();
	var price = $("#price").val();
	var proposal = $("#proposal").val();
	$.ajax({
		url: path + "/p",
		data: {process: "bid", projectIdStr: projectId, price: price, proposal: proposal},
		dataType: "JSON",
		type: "POST",
		async: true,
		success: function(data){
			bidReset();
			UZDialogFade(data.message);
		},
		error: function(){
			UZDialogFade("请求失败");
		},
		beforeSend: function () {
			$("#sider #bid").attr("disabled", true);
	    },
		complete:function(){
			$("#sider #bid").attr("disabled", false);
		}
	});
}