<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作  - 管理系统</title>
<%@ include file="admin-common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-main.css" />
<script>
$(function(){
	var ht=$(window).height();
	ht<800?ht=800:ht=$(window).height();
	$("#content").height(ht);
	$(".link").click(function(){
		$(this).addClass("xz");
		$(".link").not(this).removeClass("xz");
	});
/* 	$("#nav li p").click(function(){
		var li=$(this).parent("li")
		if(li.attr("class").indexOf("selected")>=0){
			li.removeClass("selected");
		}else{
			li.addClass("selected").siblings().removeClass("selected");
		}
	}); */
	$("#index-iframe").height(ht-120);
	$("#nav li .link,#htmllink .list li").click(function(){
		var link=$(this).attr("link");
		if(link!="index"){
			$("#index-iframe").show().attr("src",link);
		}else{
			$("#index-iframe").hide();
		}
	});
	$("#htmllink .list li").click(function(){
		var index=$(this).index();
		$("#nav li .link").removeClass("xz");
		$("#nav li").removeClass("selected").eq(index+1).addClass("selected").find("a").eq(0).addClass("xz");
	});
})
</script>
</head>
<body>
<div id="head">
	<div id="head-top">
		<p class="title"><a href="#"></a><span>悠哉工作管理系统</span></p>
		<p class="cz">
			<a class="uname"><i></i><span>欢迎你：<c:out value="${onlineUZAdmin.username}" /></span></a>
			<a class="logout" href="<%=path%>/uz-admin/admin-login?process=logout"><span>退出</span><i></i></a>
		</p>
	</div>
</div>
<div id="content">
	<div id="left-slider">
		<p class="indexlogo"></p>
		<div id="nav">
			<ul>
				<li class="nav-box-1">
					<p><a link="index" class="link xz"><i class="left"></i><em>管理首页</em></a></p>
				</li>
				<li class="nav-box-2">
					<p><i class="left"></i><i class="right"></i><em>项目管理</em></p>
					<dl>
						<dd><a link="<%=path%>/uz-admin/project-mag" class="link"><em>项目列表</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/project-mag?process=review_list" class="link"><em>项目审核</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/project-mag?process=review_history" class="link"><em>审核记录</em></a></dd>
					</dl>
				</li>
				<li class="nav-box-3">
					<p><i class="left"></i><i class="right"></i><em>用户管理</em></p>
					<dl>
						<dd><a link="<%=path%>/uz-admin/user-mag" class="link"><em>用户列表</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/user-mag?process=review_list" class="link"><em>用户审核</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/user-mag?process=review_history" class="link"><em>审核记录</em></a></dd>
					</dl>
				</li>
				<li class="nav-box-4">
					<p><i class="left"></i><i class="right"></i><em>系统管理</em></p>
					<dl>
						<dd><a link="<%=path%>/uz-admin/message-mag" class="link"><em>通知管理</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/feedback-mag" class="link"><em>反馈管理</em></a></dd>
					</dl>
				</li>
				<li class="nav-box-5">
					<p><i class="left"></i><i class="right"></i><em>管理员管理</em></p>
					<dl>
						<dd><a link="<%=path%>/uz-admin/admin-user-mag" class="link"><em>管理员列表</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/admin-user-mag?process=add" class="link"><em>增加管理员</em></a></dd>
						<dd><a link="<%=path%>/uz-admin/admin-user-mag?process=password" class="link"><em>修改密码</em></a></dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>
	<div id="right-main">
		<div id="htmllink">
			<iframe id="index-iframe" src="" style="display:none;"></iframe>
			<div id="main-box">
				<div class="title"><i>悠哉工作管理系统 － 首页</i></div>
				<div class="list">
					<ul>
						<li link="<%=path%>/uz-admin/project-mag" class="main-box-1">
							<p><i></i><span>项目管理</span></p>
						</li>
						<li link="<%=path%>/uz-admin/user-mag" class="main-box-2">
							<p><i></i><span>用户管理</span></p>
						</li>
						<li  link="<%=path%>/uz-admin/sys-mag" class="main-box-3">
							<p><i></i><span>系统管理</span></p>
						</li>
						<li style="margin-right:0;" link="<%=path%>/uz-admin/admin-user-mag" class="main-box-4">
							<p><i></i><span>管理员管理</span></p>
						</li>
					</ul>
				</div>
			</div>
			<p id="copyright">
				<span>版权所有© 上海悠哉工作信息技术有限公司</span>
				<span>Copyright © 2016-2018 www.uzwork.com All rights reserved沪ICP备16037975号</span>
			</p>
		</div>
	</div>
</div>
</body>
</html>