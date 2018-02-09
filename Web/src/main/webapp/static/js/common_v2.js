$(function(){
//	if(!window.localStorage){
//		alert("浏览器版本过低，请更新您的浏览器");
//	}
	// 替换首页的logo和下拉图标
	$("#ind-head .logo img").attr("src", staticPath + "/img/logo.png");
	$("#ind-head .right .me i").css("background-image", "url("+staticPath + "/img/head-select.png"+")");
	
	// ajax extend
//	var _ajax = $.ajax;
//	$.ajax = function(opt){
//        var _opt = $.extend(opt, {
//        	dataType: "JSON",
//        	method: "POST",
//        	headers: {"Accept-Channel": "browser", "Access-Token": sessionStorage.getItem("localAccessToken")},
//        	error: function(){UZDialogFade("\u8BF7\u6C42\u5931\u8D25");}
//        });
//        return _ajax(_opt);
//    };
    
    $.ajaxSetup({
    	dataType: "JSON",
    	method: "POST",
    	headers: {"Accept-Channel": "browser", "Access-Token": sessionStorage.getItem("localAccessToken")}
    });
    $(document).ajaxError(function(){
    	UZDialogFade("\u8BF7\u6C42\u5931\u8D25");
    });
    
	// initialize access token if it is null
	var token = sessionStorage.getItem("localAccessToken");
	if(token == null || token.length <= 0) {
		$.ajax({url: mobilePath + "/token"}).done(function(data, status) {
			if(data.value.length > 0) {
				sessionStorage.setItem("localAccessToken", data.value);
				initialization();
			}
		});
	}
	else {
		initialization();
	}
	
	
	// 显示隐藏筛选的下拉菜单
	$("#filter li").hover(
		function() {
			$(this).find("dl").css("display","block");
			$(this).children("span").css("color","#01A2F2");
		},
		function() {
			$(this).find("dl").css("display","none");
			$(this).children("span").css("color","#444");
		}
	);
	
	// replace \r\n
	$(".htmlRN").each(function(){
		$(this).html($(this).html().replace(/\r\n|\n/g, "<br>"));
	});
	
	// 显示隐藏会员中心的下拉菜单
	$("#head .right .me").hover(
		function() {
			$(this).find("dl").css("display","block");
		},
		function() {
			$(this).find("dl").css("display","none");
		}
	);
	
	// select the navigation item
	$("#head #nav li").each(function(){
		var href = $(this).find("a").attr("href");
		var url = window.location.href;
		if(url == href) {
			$(this).addClass("sel");
			return false;
		}
		else if((url.indexOf("ul") > -1 && href.indexOf("ul") > -1) || (url.indexOf("pl") > -1 && href.indexOf("pl") > -1)) {
			// special logical is for category project/user
			$(this).addClass("sel");
			return false;
		}
	});
});

// If the access user has been saved before, then get the latest user information. If the user is inactive now, then update previous access token.
function initialization() {
	var token = sessionStorage.getItem("localAccessToken");
	if(token != null && token.length > 0) {
		if(sessionStorage.getItem("accessUser") == null) {
			$.ajax({url: mobilePath + "/init"}).done(function(data, status) {
				if(data.value.user != null) {
					sessionStorage.setItem("accessUser", JSON.stringify(data.value.user));
				}
				else {
					sessionStorage.setItem("accessUser", JSON.stringify({"userId": 0}));
					if(data.value.newToken != null) {
						sessionStorage.setItem("localAccessToken", data.value.newToken);
					}
				}
			});
		}
	}
}