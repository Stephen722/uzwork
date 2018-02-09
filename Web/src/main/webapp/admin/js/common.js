$(function(){
	placeholderInt();
	$(document).on("blur",".placeholder",function(){
		if($(this).val()==$(this).attr("placeholder")){
			return false;
		}
	});
	/*顶部注册下拉框*/
	$(".reg-drop-down").mouseover(function(){
		$(".reg-select").show();
	});
	$(".reg-drop-down").mouseleave(function(){
		$(".reg-select").hide();
	});
	
	$(".float_menu li").hover(function(){
		$(this).addClass("xz").siblings().removeClass("xz");
	},function(){
		$(this).removeClass("xz");
	});
	$(".float_menu").mouseleave(function(){
		$(".float_menu li").eq(1).addClass("xz").siblings().removeClass("xz");
	});
	
	// Define the focus and blur style for input
	$(document).on("focus","input[type=text],input[type=password],textarea",function(){
		var cls=$(this).attr("class");
		if(cls){
			if(cls.indexOf("input-uz") >= 0){
				$(this).addClass("input-focus").removeClass("error-bd");
			}else{
				$(this).parents(".input-uz").addClass("input-focus").removeClass("error-bd");
			}
		}else{
			$(this).parents(".input-uz").addClass("input-focus").removeClass("error-bd");
		}
	});
	$(document).on("blur","input[type=text],input[type=password],textarea",function(){
		var cls=$(this).attr("class");
		if(cls){
			if(cls.indexOf("input-uz") >= 0){
				$(this).removeClass("input-focus");
			}else{
				$(this).parents(".input-uz").removeClass("input-focus");
			}
		}else{
			$(this).parents(".input-uz").removeClass("input-focus");
		}
	});

	$(".floatPosition1").hover(function(){
		$(".flatQRcode").show();	
	},function(){
		$(".flatQRcode").hide();
	});
	var codeimg=0;
	$(document).on("click","#codeimgchange",function()
	{
		codeimg++;
		$(this).attr("src",path+"/pd/appointment?process=generateImageCode&random="+codeimg);
	});
	
	/*密码输入限制*/
	$(".passwordReplace").blur(function()
  	{
    	var pswd=$(this).val();
		var tx=trimStr(pswd);
		$(this).val(tx);
    });
	
	/*验证码输入限制*/
	$(".CodeReplace").keyup(function(){
		var tsvl=$(this).val();
		var nwvl=codeStr(tsvl);
		$(this).val(nwvl);
	});
	
	/*关键字搜索输入限制*/
	$(".SerachReplace").blur(function()
	{
		var text=$(this).val();
		var searchtext=searchStr(text);
		$(this).val(searchtext);
	});
	
	$(".IsEmail").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(!isemail(val)){
				t.errorShow("请输入正确的邮箱地址");
			}else{
				t.addClass("icon-correct");
			}
		}
	});
	
	$(".IsPassword").focus(function(){
		$(".password-icon").attr("class","password-icon");
		$(".default-text em").text("建议使用字母、数字或符号组合，6-20位字符");
	});
	
	$(".IsPassword").keyup(function(){
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
			//t.errorShow("请输入6-20位密码");
		}
	});
	
	
	$(".IsPassword").blur(function(){
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
			//t.errorShow("请输入6-20位密码");
		}
	});
	
	
	$(".IsconfirmedPassword").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		var paswd=$(".IsPassword").val();
		var cmpaswd=t.val();
		if(cmpaswd!='')
		{
			if(cmpaswd.length>=6){
				if(paswd!==cmpaswd && cmpaswd!=''){
					//t.errorShow("两次输入密码不一致");
				}else if(cmpaswd!=''){
					t.addClass("icon-correct");
				}
			}else{
				//t.errorShow("请输入6-20位密码");
			}
		}
	});
	
	$(document).on("blur",".isRealname",function(){ 
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(isrealName(val)){
				t.addClass("icon-correct");
			}else{
				t.errorShow("请输入真实姓名");
			}
		}
	});
	
	$(".IsfirmName").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(isfirmName(val)){
				t.addClass("icon-correct");
			}else{
				t.errorShow("请输入企业真实名称");
			}
		}
	});
	
	$(".IsSMSCode").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(verifyCode(val)){
				t.addClass("icon-correct");
			}else{
				t.errorShow("短信验证码错误");
			}
		}
	});
	
	$(document).on("blur",".iscellPhone",function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(iscellPhone(val)){
				t.addClass("icon-correct");
			}else{
				t.errorShow("请输入正确的手机号");
			}
		}
	});
	
	$(".IsidCard").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(getIdCardInfo(val).isTrue){
				t.addClass("icon-correct");
			}else{
				t.errorShow("请输入正确的身份证号");
			}
		}
	});
	
	$(".Isusername").blur(function(){
		var t=$(this);
		t.removeClass("icon-correct");
		var val=t.val();
		if(val!=''){
			if(isuserName(val)){
				t.addClass("icon-correct");
			}else{
				t.errorShow("请输入2-20位数字、字母或下划线组成的用户名");
			}
		}
	});
	
	$(document).on("focus",".window dd input",function(){
		$(this).siblings(".errorText").hide().text("");
	});
	
	$(document).on("focus",".error-bd",function(){
		$(this).removeClass("error-bd");
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

/*功能类*/

function addCookie(objName, objValue, objHours){//添加cookie 
    var str = objName + "=" + escape(objValue); 
    if (objHours > 0) {//为0时不设定过期时间，浏览器关闭时cookie自动消失 
        var date = new Date(); 
        var ms = objHours * 3600 * 1000; 
        date.setTime(date.getTime() + ms); 
        str += "; expires=" + date.toGMTString(); 
    } 
    document.cookie = str; 
} 
function getCookie(objName){//获取指定名称的cookie的值 
    var arrStr = document.cookie.split("; "); 
    for (var i = 0; i < arrStr.length; i++) { 
        var temp = arrStr[i].split("="); 
        if (temp[0] == objName) 
            return unescape(temp[1]); 
    } 
} 
function clearCookie(){  //清空cookie
    var cookies = document.cookie.split(";");  
    for (var i = 0; i < cookies.length; i++) {  
        var cookie = cookies[i];  
        var eqPos = cookie.indexOf("=");  
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;  
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";  
    }  
	if(cookies.length > 0)  
	{  
	    for (var i = 0; i < cookies.length; i++) {  
	        var cookie = cookies[i];  
	        var eqPos = cookie.indexOf("=");  
	        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;  
	    var domain = location.host.substr(location.host.indexOf('.'));  
	        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; domain=" + domain;  
	    }  
	}  
}
function Geturlkey(name){ //获取地址栏参数
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/*符合规则，返回true或false类*/

function issymbol(parameter){  //是否包含特殊字符或符号（可用密码强度等）
	var special=false;var yz=/^[\d|a-z|A-Z]*$/;
	if(!yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isnumber(parameter){  //是否包含数字（可用密码强度等）
	var special=false;var yz=/\d+/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isenglish(parameter){  //是否包含小写英文（可用密码强度等）
	var special=false;var yz=/[a-z]+/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isenglishCapital(parameter){  //是否包含大写英文（可用密码强度等）
	var special=false;var yz=/[A-Z]+/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isemail(parameter){  //是否符合邮箱规则
	var special=false;var emailPattern = '^[0-9a-zA-Z\-\_\.]{2,}@(([0-9a-zA-Z]{2,})[.])+[a-z]{2,4}$';
	if(parameter.match(emailPattern)){special=true;}else{special=false;}
	return special;
}
function iscellPhone(parameter){ //是否符合手机号规则
	var special=false;var yz=/^(((13[0-9]{1})|(14[5|7]{1})|(17[0|1|3|5|6|7|8]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isRealName(parameter){ //是否符合真实姓名规则
	var special=false;var yz=/^[\u4E00-\u9FA5]{2,6}$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isfirmName(parameter){ //是否符合企业名规则称
	var special=false;var yz=/^[\u4E00-\u9FA5\(\)\（\）]{4,30}$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isUsername(parameter){ //是否符合用户名规则
	var special=false;var yz=/^[a-zA-Z0-9_\u4E00-\u9FA5]{2,16}$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isAdminUsername(parameter){ //是否符合管理员用户名规则
	var special=false;var yz=/^[a-zA-Z0-9_]{3,16}$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function twodecimals(parameter){ //允许1到2位小数 （可用年化收益率等）
	var special=false;var yz=/^\d+(\.\d{1,2})?$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
}
function isInt(parameter){ //只允许整数 （可用存续期限等）
	var special=false;var yz=/^\d{1,}$/;
	if(yz.test(parameter)){special=true;}else{special=false;}
	return special;
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

/*传入任意字符，返回替换类*/

function searchStr(str){ //返回 中文、数字、字母 （可用关键词搜索等）
	return str.replace(/([^a-z|A-Z|0-9|\u4E00-\u9FA5])/g,"");
}
function codeStr(str){ //返回 英文、数字 （可用验证码等）
	return str.replace(/([^a-z|A-Z|0-9])/g,"");
}
function trimStr(str){ //返回 去除前后空格 （可用设置密码等）
	return str.replace(/(^\s*)|(\s*$)/g,"");
}
function trimStrs(str){ //返回 去除包含的所有空格 （可用密码等）
	return str.replace(/\s/g,"");
}
function ellipsis(val,number){ //超出字数省略（简）
	var maxwidth=number;  
	var text=val;
	if(text.length>maxwidth)
	{   
		text=text.substring(0,maxwidth);
		text=text+'...';  
	} 
	return text;
}

/*公用事件类*/


//该方法为 错误信息显示  调用方式： $("输入框id或class").errorShow 前提是必须设置固定格式的html
/*
 * 1.其兄弟元素或者其父元素的兄弟元素有一个类名为 error-msg 的错误信息展示区
 * 2.其类名或者其父元素类名必须有一个 类名为：input-uz
*/
$.fn.errorShow=function(text){ 
	var e=$(this).siblings(".error-msg");
	var t=$(this).parent().siblings(".error-msg");
	var s=$(this).parents(".input-uz").siblings(".error-msg");
	if(e.length > 0){
		e.show().text(text);
		$(this).focus(function(){
			e.text('').hide();
		});
	}else if(t.length > 0){
		t.show().text(text);
		$(this).focus(function(){
			t.text('').hide();
		});
	}else if(s.length > 0){
		s.show().text(text);
		$(this).focus(function(){
			s.text('').hide();
		});
	}	
	var cls=$(this).attr("class");
	if($(this).attr("class")){
		if(cls.indexOf("input-uz") >= 0){
			$(this).addClass("error-bd");
		}else{
			$(this).parents(".input-uz").addClass("error-bd");
		}
	}else{
		$(this).parents(".input-uz").addClass("error-bd");
	}
}


/*公用翻页HTML*/
function pageHtml(MaxLength,PageLength,Epage,Fn){ //MaxLength = 最大条目数，PageLength = 每页多少条，Epage = 当前第几页，Fn = 点击后执行的方法
	if(MaxLength>0){
		var pagelist=parseInt(MaxLength/PageLength);
		if(MaxLength%PageLength!=0){
			pagelist=parseInt(pagelist)+1;
		}
		var pageHtml='';
		var pagea='';
		var pageb='';
		var backhtml='';
		var nexthtml='';
		Epage=Number(Epage);
		if(pagelist>0){
			if(Epage>1 && Fn){
				var b=Epage-1;
				var e=Fn+'('+b+')';
				backhtml="<span onclick='"+e+"'>上一页</span>";
			}else{
				backhtml="<em>上一页</em>";
			}
			if(Epage<pagelist && Fn){
				var n=Epage+1;
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
						if(x==Epage){
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
				if(Epage<6){
					for(var x=1;x<8;x++){
						if(x==Epage){
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
				}else if(pagelist-Epage >= 6){
					var pl=Epage+4;
					var pt=Epage-3;
					for(var x=Epage;x<pl;x++){
						if(x==Epage){
							pagea+="<b>"+x+"</b>";	
						}else if(Fn){
							var e=Fn+'('+x+')';
							pagea+="<a onclick='"+e+"'>"+x+"</a>";
						}else{
							pagea+="<a>"+x+"</a>";
						}
					}
					for(var x=pt;x<Epage;x++){
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
					var pt=Epage-3;
					for(var x=pt;x<=pagelist;x++){
						if(x==Epage){
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
	pageHtml=backhtml+pagea+nexthtml;
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
		var newHtml = "<div class='uz-border-fn'><a onclick='UZDialogClose();' class='close'></a><p class='title'>"+title+"</p>";
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
String.prototype.endWith=function(s){
	if(s==null||s==""||this.length==0||s.length>this.length) {
		return false;
	}
		
	if(this.substring(this.length-s.length)==s) {
		return true;	
	}    
	else {
		return false;	
	}
	return true;
}

String.prototype.startWith=function(s){
	if(s==null||s==""||this.length==0||s.length>this.length) {
		return false;
	}
	   
	if(this.substr(0,s.length)==s) {
		return true;
	}
	else {
		return false;
	}
	return true;
}