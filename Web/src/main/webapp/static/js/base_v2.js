/**
 * the base script contains some basic functions for web usage, none of any business.
 */
$(function(){
	// define the focus and blur style for input
	$(document).on("focus","input[type=text],input[type=password],textarea",function(){
		if($(this).hasClass("input") >= 0){
			$(this).addClass("input").removeClass("error-bd").removeClass("warn-bd");
		}else{
			$(this).parents(".input").addClass("focus").removeClass("error-bd").removeClass("warn-bd");
		}
	});
	
	$(document).on("blur","input[type=text],input[type=password],textarea",function(){
		if($(this).hasClass("input") >= 0){
			$(this).removeClass("focus");
		}
		else{
			$(this).parents(".input").removeClass("focus");
		}
	});
	
	$(".email").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(!isEmail(val)){
				t.message("请输入正确的邮箱地址");
			}else{
				t.addClass("icon-correct");
			}
		}
	});
	
	$(".password").focus(function(){
		$(".password-icon").attr("class","password-icon");
		$(".default-text em").text("建议使用字母、数字或符号组合，6-20位字符");
	});
	
	$(".isPassword").keyup(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		var o=/^([\d]*|[a-z]*|[A-Z]*)$/; //同一种（数字或字母或大写字母）开始到结束
		if(val.length>=6){
			if(o.test(val) && !issymbol(val) || issymbol(val) && !isnumber(val) && !isenglish(val) && !isenglishCapital(val)){
				$(".password-icon").addClass("password-icon-weak");
				$(".default-text em").text("建议输入数字、字母或符号两种及以上的组合");
			}else if(issymbol(val) && isnumber(val) && !isenglish(val) && !isenglishCapital(val)){ //12
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(issymbol(val) && isenglish(val) && !isnumber(val) && !isenglishCapital(val)){ //13
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(issymbol(val) && isenglishCapital(val) && !isnumber(val) && !isenglish(val)){ //14
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isnumber(val) && isenglish(val) && !issymbol(val) && !isenglishCapital(val)){ //23
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isnumber(val) && isenglishCapital(val) && !issymbol(val) && !isenglish(val)){ //24
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isenglish(val) && isenglishCapital(val) && !issymbol(val) && !isnumber(val)){ //34
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else{
				$(".password-icon").addClass("password-icon-Tough");
				$(".default-text em").text("您的密码很安全");
			}
			//t.addClass("icon-correct");
		}else if(val!=''){
			//t.message("请输入6-20位密码");
		}
	});
	
	
	$(".isPassword").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		var o=/^([\d]*|[a-z]*|[A-Z]*)$/; //同一种（数字或字母或大写字母）开始到结束
		if(val.length >= 6){
			if(o.test(val) && !issymbol(val) || issymbol(val) && !isnumber(val) && !isenglish(val) && !isenglishCapital(val)){
				$(".password-icon").addClass("password-icon-weak");
				$(".default-text em").text("建议输入数字、字母或符号两种及以上的组合");
			}else if(issymbol(val) && isnumber(val) && !isenglish(val) && !isenglishCapital(val)){ //12
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(issymbol(val) && isenglish(val) && !isnumber(val) && !isenglishCapital(val)){ //13
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(issymbol(val) && isenglishCapital(val) && !isnumber(val) && !isenglish(val)){ //14
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isnumber(val) && isenglish(val) && !issymbol(val) && !isenglishCapital(val)){ //23
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isnumber(val) && isenglishCapital(val) && !issymbol(val) && !isenglish(val)){ //24
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else if(isenglish(val) && isenglishCapital(val) && !issymbol(val) && !isnumber(val)){ //34
				$(".password-icon").addClass("password-icon-medium");
				$(".default-text em").text("可以使用三种以上的组合来提高安全强度");
			}else{
				$(".password-icon").addClass("password-icon-Tough");
				$(".default-text em").text("您的密码很安全");
			}
			//t.addClass("icon-correct");
		}else if(val!=''){
			t.message("请输入6-20位密码");
		}
	});
	
	
	$(".isConfirmedPassword").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		var paswd=$(".isPassword").val();
		var cmpaswd=t.val();
		if(cmpaswd!='')
		{
			if(cmpaswd.length>=6){
				if(paswd!==cmpaswd && cmpaswd!=''){
					//t.message("两次输入密码不一致");
				}else if(cmpaswd!=''){
					t.addClass("icon-correct");
				}
			}else{
				//t.message("请输入6-20位密码");
			}
		}
	});
	
	$(".isCellPhone").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(isCellPhone(val)){
				t.addClass("icon-correct");
			}else{
				t.message("请输入正确的手机号");
			}
		}
	});
	
	$(".isIdCard").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(getIdCardInfo(val).isTrue){
				t.addClass("icon-correct");
			}else{
				t.message("请输入正确的身份证号");
			}
		}
	});
	
	$(".isUsername").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(isUserName(val)){
				t.addClass("icon-correct");
			}else{
				t.message("请输入2-20位数字、字母或下划线组成的用户名");
			}
		}
	});
});

function placeholderInt(){
	if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
		$.each($('[placeholder]'),function(){
			if($(this).attr("type")=="password"){
				var pld=$(this).attr("placeholder");
				var plw=$(this).width();
				var plh=$(this).height();
				var plt="<input class='hidpld' type='text' value='"+pld+"' style='width:"+plw+"px;height:"+plh+"px;color:#ddd;' />";
				$(this).addClass("hidpsd").css({"display":"none"});
				$(this).after(plt);
			}else{
				$(this).addClass("placeholder");
				var input = $(this);
		        if (input.val() == '' || input.val() == input.attr('placeholder')) {
		            input.addClass('placeholder');
		            input.val(input.attr('placeholder')).css({"color":"#ddd"});
		        }
				
			}
		});
		
		$(document).on("focus",".placeholder",function(){
			 var input = $(this);
		        if (input.val() == input.attr('placeholder')) {
		            input.val('').css({"color":"#666"});;
		        }
		});
		$(document).on("blur",".placeholder",function(){
			var input = $(this);
	        if (input.val() == '' || input.val() == input.attr('placeholder')) {
	            input.val(input.attr('placeholder')).css({"color":"#ddd"});;
	            
	        }
		});
		
		$(document).on("focus",".hidpld",function(){
			$(this).hide().siblings(".hidpsd").show().focus();
		});
		$(document).on("blur",".hidpsd",function(){
			if($(this).val()==''){
				$(this).hide().siblings(".hidpld").show();
			}
		});   
	};
}

function placeholderSupport(){  // 用于判断浏览器是否支持 placeholder
    return 'placeholder' in document.createElement('input');
}
// get url parameter by name
function getUrlParameter(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null) {
    	 	return  unescape(r[2]);
    	 }
     else {
    	 	return null;
     }
}
// check symbol
function isSymbol(str){
	var rtn = false;
	var reg = /^[\d|a-z|A-Z]*$/;
	if(!reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check number 
function isNumber(str){
	var rtn = false;
	var reg = /\d+/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check English char
function isEnglish(str){
	var rtn = false;
	var reg = /[a-z]+/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check capital English char
function isEnglishCapital(str){
	var rtn = false;
	var reg = /[A-Z]+/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check email
function isEmail(str){
	var rtn = false;
	var reg = '^[0-9a-zA-Z\-\_\.]{2,}@(([0-9a-zA-Z]{2,})[.])+[a-z]{2,4}$';
	if(str.match(reg)){
		rtn = true;
	}
	return rtn;
}
// check cell phone
function isCellPhone(str){
	var rtn = false;
	var reg = /^(((13[0-9]{1})|(14[5|7]{1})|(17[0|1|3|5|6|7|8]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check Chinese name
function isRealName(str){
	var rtn = false;
	var reg = /^[\u4E00-\u9FA5]{2,6}$/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check user name
function isUsername(str){ //是否符合用户名规则
	var rtn = false;
	var reg = /^[a-zA-Z0-9_\u4E00-\u9FA5]{2,16}$/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}
// check decimal
function isTwoDecimal(str){
	var rtn = false;
	var reg = /^\d+(\.\d{1,2})?$/;
	if(reg.test(str)){
		rtn=true;
	}
	return rtn;
}
// check integer
function isInt(str){
	var rtn = false;
	var reg = /^\d{1,}$/;
	if(reg.test(str)){
		rtn = true;
	}
	return rtn;
}

function getIdCardInfo(cardNo) { //身份证验证  调用方式： getIdCardInfo(身份证号).isTrue  提供以下接口
  var info = {
		isTrue : false, // 身份证号是否有效。默认为 false
		year : null,// 出生年。默认为null
		month : null,// 出生月。默认为null
		day : null,// 出生日。默认为null
		isMale : false,// 是否为男性。默认false
		isFemale : false // 是否为女性。默认false
	};
	
	if (!cardNo && 15 != cardNo.length && 18 != cardNo.length) {
		info.isTrue = false;
		return info;
	}
	
	if (15 == cardNo.length) {
		var year = cardNo.substring(6, 8); //年
		var month = cardNo.substring(8, 10); // 月
		var day = cardNo.substring(10, 12); //日
		var p = cardNo.substring(14, 15); // 性别位
		var birthday = new Date(year, parseFloat(month) - 1, parseFloat(day));
		// 对于老身份证中的年龄则不需考虑千年虫问题而使用getYear()方法
		if (birthday.getYear() != parseFloat(year)
				|| birthday.getMonth() != parseFloat(month) - 1
				|| birthday.getDate() != parseFloat(day)) {
			info.isTrue = false;
		} else {
			info.isTrue = true;
			info.year = birthday.getFullYear();
			info.month = birthday.getMonth() + 1;
			info.day = birthday.getDate();
			if (p % 2 == 0) {
				info.isFemale = true;
				info.isMale = false;
			} else {
				info.isFemale = false;
				info.isMale = true;
			}
		}
		return info;
	}
	
	if (18 == cardNo.length) {
		var year = cardNo.substring(6, 10); //年
		var month = cardNo.substring(10, 12); //月
		var day = cardNo.substring(12, 14); //日
		var p = cardNo.substring(14, 17); //性别
		var birthday = new Date(year, parseFloat(month) - 1, parseFloat(day)); //出生日期
		// 这里用getFullYear()获取年份，避免千年虫问题
		if (birthday.getFullYear() != parseFloat(year)
				|| birthday.getMonth() != parseFloat(month) - 1
				|| birthday.getDate() != parseFloat(day)) {
			info.isTrue = false;
			return info;
		}

		var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子
		var Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X

		// 验证校验位
		var sum = 0; // 声明加权求和变量
		var _cardNo = cardNo.split("");

		if (_cardNo[17].toLowerCase() == 'x') {
			_cardNo[17] = 10;// 将最后位为x的验证码替换为10方便后续操作
		}
		for ( var i = 0; i < 17; i++) {
			sum += Wi[i] * _cardNo[i];// 加权求和
		}
		var i = sum % 11;// 得到验证码所位置

		if (_cardNo[17] != Y[i]) {
			return info.isTrue = false;
		}

		info.isTrue = true;
		info.year = birthday.getFullYear();
		info.month = birthday.getMonth() + 1;
		info.day = birthday.getDate();
		//双数为女性，单数为男性
		if (p % 2 == 0) {
			info.isFemale = true;
			info.isMale = false;
		} else {
			info.isFemale = false;
			info.isMale = true;
		}
		return info;
	}
	return info;
}
// get the limited string
function ellipsis(str, length){
	var max = length;  
	var text = str;
	if(text.length > max) {   
		text = text.substring(0, max);
		text = text + '...';  
	} 
	return text;
}
// check start string
String.prototype.startsWith=function(str){
	if(str == null || str == "" || this.length == 0 || str.length > this.length) {
		return false;
	}
	else if(this.substr(0, str.length) == str) {
		return true;
	}
	return false;
}
//check end string
String.prototype.endsWith = function(str){
	if(str == null || str == "" || this.length == 0 || str.length > this.length) {
		return false;
	}
	else if(this.substring(this.length - str.length) == str) {
		return true;
	}
	return false;
}

// msgClass could be error and warn, default error.
// <p class="input"><input type="text" /><span class="msg error/warn"></span></p>
$.fn.message = function(msg, msgClass){
	var m = $(this).siblings(".msg");
	var pt = $(this).parents(".input");
	var classBd = msgClass+"-bd";
	if(m != null && m.length > 0) {
		if(msgClass == null || msgClass.length == 0) {
			msgClass = 'error';
			classBd = 'error-bd';
		}
		m.show().text(msg);
		m.addClass(msgClass);
		$(this).focus(function(){
			m.text('').hide();
			if($(this).hasClass(classBd)) {
				$(this).removeClass(classBd);
			}
			else if(pt.hasClass(classBd)){
				pt.removeClass(classBd);
			}
		});
	}
	if($(this).hasClass("input") >= 0){
		$(this).addClass(classBd);
	}
	else{
		pt.addClass(classBd);
	}
}
// pagination. Fn is the event of page click
function page(totalRecords,pageSize,curPage,Fn){
	if(totalRecords>0){
		var pagelist=parseInt(totalRecords/pageSize);
		if(totalRecords%pageSize!=0){
			pagelist=parseInt(pagelist)+1;
		}
		var pageHtml='';
		var pagea='';
		var pageb='';
		var backhtml='';
		var nexthtml='';
		curPage=Number(curPage);
		if(pagelist>0){
			if(curPage>1 && Fn){
				var b=curPage-1;
				var e=Fn+'('+b+')';
				backhtml="<span onclick='"+e+"'>上一页</span>";
			}else{
				backhtml="<em>上一页</em>";
			}
			if(curPage<pagelist && Fn){
				var n=curPage+1;
				var e=Fn+'('+n+')';
				nexthtml="<span onclick='"+e+"'>下一页</span>";
			}else{
				nexthtml="<em>下一页</em>";
			}
			if(pagelist<10){
				if(pagelist==1){
					pagea+="<b>1</b>";
				}else{
					for(var x=1;x<pagelist+1;x++){
						if(x==curPage){
							pagea+="<b>"+x+"</b>";	
						}else if(Fn){
							var e=Fn+'('+x+')';
							pagea+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pagea+="<a>"+x+"</a>";	
						}
					}
				}	
			}else{
				if(curPage<6){
					for(var x=1;x<8;x++){
						if(x==curPage){
							pagea+="<b>"+x+"</b>";	
						}else if(Fn){
							var e=Fn+'('+x+')';
							pagea+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pagea+="<a>"+x+"</a>";	
						}
					}
					if(Fn){
						var t=Fn+'('+pagelist+')';
						var pagelt="<i>...</i><a onclick='"+t+"'>"+pagelist+"</a>";
					}else{
						var pagelt="<i>...</i><a>"+pagelist+"</a>";
					}
					pagea=pagea+pagelt;
				}else if(pagelist-curPage >= 6){
					var pl=curPage+4;
					var pt=curPage-3;
					for(var x=curPage;x<pl;x++){
						if(x==curPage){
							pagea+="<b>"+x+"</b>";	
						}else if(Fn){
							var e=Fn+'('+x+')';
							pagea+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pagea+="<a>"+x+"</a>";
						}
					}
					for(var x=pt;x<curPage;x++){
						if(Fn){
							var e=Fn+'('+x+')';
							pageb+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pageb+="<a>"+x+"</a>";
						}
					}
					if(Fn){
						var t=Fn+'('+pagelist+')';
						var pagelt="<i>...</i><a onclick='"+t+"'>"+pagelist+"</a>";
						var w=Fn+'(1)';
						var pagetp="<a onclick='"+w+"'>1</a><i>...</i>";
					}else{
						var pagelt="<i>...</i><a>"+pagelist+"</a>";
						var pagetp="<a>1</a><i>...</i>";
					}
					pagea=pagetp+pageb+pagea+pagelt;
				}else{
					var pt=curPage-3;
					for(var x=pt;x<=pagelist;x++){
						if(x==curPage){
							pagea+="<b>"+x+"</b>";	
						}else if(Fn){
							var e=Fn+'('+x+')';
							pagea+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pagea+="<a>"+x+"</a>";
						}
					}
					if(Fn){
						var w=Fn+'(1)';
						var pagetp="<a onclick='"+w+"'>1</a><i>...</i>";
					}else{
						var pagetp="<a>1</a><i>...</i>";
					}
					pagea=pagetp+pagea;
				}
			}
		}
	}else{
		pageHtml='';
	}
	pageHtml = "<p class='page'>" + backhtml+pagea+nexthtml + "</p>";
	return pageHtml;
}

/*loading*/
function loading(toggle){
	if(toggle){
		var cl = $(window).width() / 2 - 70;
		var ct = $(window).height() / 2 - 60;
		$("body").append('<p id="loading"><span><img src="'+staticPath+'/img/loading.gif" /> loading......</span><i></i></p>');
		$("#loading").css({"left": cl + "px","top":ct + "px"});
		$("body input").each(function(){
			$(this).attr("readonly", true);
		});
		$("body textarea").each(function(){
			$(this).attr("readonly", true);
		});
	}
	else {
		$("#loading").remove();
		$("body input").each(function(){
			$(this).attr("readonly", false);
		});
		$("body textarea").each(function(){
			$(this).attr("readonly", false);
		});
	}
}

/* 定义弹框 */
$.fn.UZDialog=function() {
	if(!$(this).hasClass("uz-dialog-fn")) {
		$(this).addClass("uz-dialog-fn");
		var title = "UZWork Information";
		if(typeof($(this).attr("title")) != "undefined") {
			title = $(this).attr("title");
		}
		var w = $(this).width();
		var h = $(this).height();
		var newHtml = "<div class='uz-border-fn' title='' tip=''><a onclick='UZDialogClose();' class='close'></a><p class='title'>"+title+"</p>";
		newHtml += $(this).html() + "</div>";
		$(this).html(newHtml);
		if(w && w >0) {
			$(this).children(".uz-border-fn").width(w);
		}
		if(h && h > 0) {
			$(this).children(".uz-border-fn").height(h);
		}
	}
	// 计算弹框位置
	var ch = $(this).height(), cw = $(this).width();
	var sw = $(window).width(), sh = $(window).height(); 
  	var st = $(document).scrollTop();  
  	var cl = (sw - cw) / 2;  
  	var ct = (sh - ch) / 2; 
//  	alert("ch="+ch+" cw="+cw+" sw="+sw+" sh="+sh+" st="+st+" cl="+cl+" ct="+ct);
  	$(this).css({"left": cl + "px","top":ct + "px"});
	$(this).show();
	UZDialogBG("show");
}
function UZDialogClose(){
	$(".uz-dialog").hide();	
	$(".uz-dialog-fn").hide();	
	UZDialogBG("hide");
}
function UZDialogBG(toggle){
	if(toggle == "show"){
		if($(".uz-dialog-bg").length==0){
			var html='<p class="uz-dialog-bg"></p>';
			$("body").append(html);
			$(".uz-dialog-bg").show();
			$("html,body").addClass("uz-dialog-scroll"); 
		}else{
			$(".uz-dialog-bg").fadeIn(300);
		}
	}else{
		$(".uz-dialog-bg").remove();
		$("html,body").removeClass("uz-dialog-scroll"); 
	}
}
var uzbox_index=0;
function UZDialogFade(message){
	uzbox_index++;
	var wh=$(window).height();
	var ww=$(window).width();
	var html="<div class='uzbox uzbox"+uzbox_index+"'><p><span class='uzbox-bg'></span><span class='uzbox-text'>"+message+"</span></p></div>";
	$("body").append(html);
	$(".uzbox"+uzbox_index+"").animate({opacity:1},500,function() {
		var t=$(this);
		setTimeout(function(){
			t.animate({opacity:0},1000);
		}, 2000);
		setTimeout(function(){
			t.remove();
		},3000);
	});
}
function UZDialogInfo(message, title, w, h) {
	if($(".uz-dialog").length > 0){
		$(".uz-dialog").each(function(){
			this.remove();
		});
	}
	var dTitle = "UZWork Information";
	if(typeof(title) != "undefined") {
		dTitle = title;
	}
	var newHtml = "<div class='uz-dialog'><div class='uz-border'><a onclick='UZDialogClose();' class='close'></a><p class='title'>"+dTitle+"</p>";
	newHtml += "<span class='text'>"+message+"</span></div></div>";
	$("body").append(newHtml);
	UZDialogBG("show");
    if(w > 0) {
    	 $(".uz-border").css({"width": w+"px"});
    }
    if(h > 0) {
    	 $(".uz-border").css({"height": h+"px"});
    }
	$(".uz-dialog").show();	
}

function UZDialogConfirm(message, title, callback, parm, w, h) {
	if($(".uz-dialog").length > 0){
		$(".uz-dialog").each(function(){
			this.remove();
		});
	}
	var dTitle = "UZWork Information";
	if(typeof(title) != "undefined") {
		dTitle = title;
	}
	var newHtml = "<div class='uz-dialog'><div class='uz-border'><a onclick='UZDialogClose();' class='close'></a><p class='title'>"+dTitle+"</p>";
	newHtml += "<span class='text'>"+message+"</span><button class='btn yes'>确定</button><button class='btn' onclick='UZDialogClose();'>取消</button></div></div>";
	$("body").append(newHtml);
	UZDialogBG("show");
    if(w > 0) {
    	 $(".uz-border").css({"width": w+"px"});
    }
    if(h > 0) {
    	 $(".uz-border").css({"height": h+"px"});
    }
	$(".uz-dialog .uz-border .yes").on("click", function(){
		callback(parm);
		UZDialogClose();
	});
    $(".uz-dialog").show();	
	
}
