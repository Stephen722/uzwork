<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>华纳众金  - 管理员管理</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$("#username").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("登录名不能为空");
		}
		else if(!isAdminUsername(val)){
			t.errorShow("请输入3-16位数字、字母或下划线组成的登录名");
		}
	});
	$("#password").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("密码不能为空");
		}
		else if(val.length < 6 || val.length > 20){
			t.errorShow("请输入6-20位密码");
		}
	});
	$("#confirmPassword").blur(function(){
		var t = $(this);
		var val = t.val();
		var pwd = $("#password").val();
		if(val != pwd) {
			t.errorShow("两次输入密码不一致");
		}
	});
	
	$(".submit").click(function(){
		var groupId = parseInt($("#groupId").val());
		var username = $("#username").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		if(username == "") {
			$("#username").errorShow("登录名不能为空");
			return false;
		}
		else if(!isAdminUsername(username)){
			$("#username").errorShow("请输入3-16位数字、字母或下划线组成的登录名");
			return false;
		}
		
		if(password == "") {
			$("#password").errorShow("密码不能为空");
			return false;
		}
		else if(password.length < 6 || password.length > 20){
			$("#password").errorShow("请输入6-20位密码");
			return false;
		}

		if(confirmPassword != password) {
			$("#confirmPassword").errorShow("两次输入密码不一致");
			return false;
		}
		
		$.ajax({
			url: path + "/uz-admin/admin-user-mag",
			data: {process: "insertAdminUser", groupId: groupId, username: username, password: password, confirmedPassword:confirmPassword},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){dialog_box("请求失败");},
			success: function(data){
				dialog_box(data.message);
				$(".submit").val("提交");
				if(data.success) {
					setTimeout('$(location).attr("href", path+"/uz-admin/admin-user-mag")', 2500);
				}
				else {
					$(".submit").attr("disabled", false);
				}
			},
			beforeSend: function () {
				$(".submit").val("提交中...");
				$(".submit").attr("disabled", true);
		    },
			complete:function(){
				// before refresh page, system should not enable submit butotn.
			}
		});
	});
});
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>新增管理员</span><span class="back" link="<%=path%>/uz-admin/admin-user-mag" id="go-back"><i></i>返回</span></p>
	<div class="container">
		<div class="module">
			<p class="label">新增管理员</p>
			<div>
				<select name="groupId" id="groupId">
					<option value="1">一般管理员</option>
					<option value="0">超级管理员</option>
				</select>
        	</div>
			<div>
				<p class="input-uz"><input type="text" id="username" value="" placeholder="请输入3-16位数字、字母或下划线组成的登录名"/></p>
         			<p class="error-msg"></p>
        		</div>
        		<div>
         		<p class="input-uz"><input type="password" id="password" value="" placeholder="请输入登录密码"/></p>
        		<p class="error-msg"></p>
        	</div>
        	<div>
         		<p class="input-uz"><input type="password" id="confirmPassword" value="" placeholder="请再次输入登录密码"/></p>
        		<p class="error-msg"></p>
       		</div>
     		<p><input type="submit" class="submit" value="提交" /></p>
      		<p class="tip">登录名只支持数字、字母或下划线</p>
        	</div>
	</div>
</div>
</body>
</html>