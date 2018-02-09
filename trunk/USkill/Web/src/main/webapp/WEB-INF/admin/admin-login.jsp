<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="description" content="悠哉工作  - 管理系统登录"/>
<meta name="keywords" content="悠哉工作  - 管理系统登录"/>
<meta name="copyright" content="本页版权归悠哉工作所有，All rights reserved" />
<meta name="author" content="悠哉工作  www.uzwork.com" />
<meta name="robots" content="all" />
<title>悠哉工作  - 管理系统登录</title>
<% String path = request.getContextPath(); %><script type="text/javascript">var path = '<%=path%>';</script>
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-base.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/login.css" />
<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.11.0.min.js"></script>
</head>
<body>
<div class="bg">
	<div id="login">
		<p class="title"><img src="<%=path%>/admin/image/admin-logo.png" /></p>
		<form id="loginForm" action="<%=path%>/uz-admin/admin-login" method="post" >
			<input type="hidden" name="process" value="login" />
			<input type="hidden" name="UZWorkToken" value="${UZWorkToken}"/>
			<div class="input-box">
				<div class="message" ><c:out value="${message}" /></div> 
				<div class="box-length">
					<i class="icon icon-name"></i>
					<p class="name-box">
						<input id="login-name" class="input-name" name="username" type="text" value="${username}" placeholder="请输入登录账号"  />
					</p>
				</div>
				<div class="box-length login-pasd cfix">
					<i class="icon icon-pasd"></i>
					<p class="name-box special-lgh">
						<input id="login-pasd" class="input-name" name="password" type="password" placeholder="请输入登录密码" />
					</p>
				</div>
				<div class="security-code">
					<p class="code-lgh"><input id="code" class="code-input" type="text" name="imageVerifyCode" id="imgcode"  placeholder="请输入验证码" value="" /></p>
					<span class="img-code"><img id="codeimgchange" src="<%=path%>/uz-admin/admin-login?process=generateImageCode" /></span>
				</div>
			</div>
			<div class="remember-user cfix">
				<span class="checked">记住用户名</span>
				<span class="check-box"><i></i></span>
			</div>
			<p><input class="input-sbt" type="submit" value="登录" /></p>	
		</form>
	</div>
	<div class="footer">
		<p>版权所有© 上海悠哉工作信息技术有限公司</p>
		<p>Copyright © 2016-2018 www.uzwork.com All rights reserved</p>
	</div>
</div>
<script>
$(function(){
	$(".bg").css({"min-height": $(window).height()+"px"});
	$(".check-box").click(function(){
		if($(this).find("i").is(":hidden")){ 
			checked=1;
			$(".check-box i").css({"display":"block"});
		}else{
			checked=0;
			$(".check-box i").hide();
		}
	});
	
	$("#codeimgchange").click(function() {
		$(this).attr("src", path+"/uz-admin/admin-login?process=generateImageCode&random="+new Date().toLocaleString());
	});
	
	$(".input-sbt").click(function(){
		var name=$("#login-name").val();
		var password=$("#login-pasd").val();
		var code=$("#code").val();
		if(name.length < 3){
			$(".message").text("请输入正确的登录账号");
			return false;
		}
		else if(password.length < 6){
			$(".message").text("请输入正确的登录密码");
			return false;
		}
		else if(code.length != 4){
			$(".message").text("请输入正确的图片验证码");
			return false;
		}
		else {
			 $("#loginForm").submit();
		}
	});
	$(".input-name").focus(function(){
		$(".message").text("");
	});
	$(".code-input").focus(function(){
		$(".message").text("");
	});
});
</script>
</body>
</html>