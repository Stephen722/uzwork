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
	$("#oldPassword").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("原密码不能为空");
		}
		else if(val.length < 6 || val.length > 20){
			t.errorShow("请输入6-20位原密码");
		}
	});
	$("#password").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("新密码不能为空");
		}
		else if(val.length < 6 || val.length > 20){
			t.errorShow("请输入6-20位新密码");
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
	
	$("#post").click(function(){
		var oldPassword = $("#oldPassword").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		if(oldPassword == "") {
			$("#oldPassword").errorShow("原密码不能为空");
			return false;
		}
		else if(oldPassword.length < 6 || oldPassword.length > 20){
			t.errorShow("请输入6-20位原密码");
		}
		
		if(password == "") {
			$("#password").errorShow("新密码不能为空");
			return false;
		}
		else if(password.length < 6 || password.length > 20){
			$("#password").errorShow("请输入6-20位新密码");
			return false;
		}

		if(confirmPassword != password) {
			$("#confirmPassword").errorShow("两次输入密码不一致");
			return false;
		}
		
		$.ajax({
			url: path + "/uz-admin/admin-user-mag",
			data: {process: "changePassword", oldPassword: oldPassword, password: password, confirmedPassword:confirmPassword},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){dialog_box("请求失败");},
			success: function(data){
				dialog_box(data.message);
				if(data.success) {
					setTimeout("location.reload()", 2500);
				}
			},
			beforeSend: function () {
				$("#post").val("提交中...");
				$("#post").attr("disabled", true);
		    },
			complete:function(){
				$("#post").val("提交");
				$("#post").attr("disabled", false);
			}
		});
	});
});
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>修改管理员密码</span><span class="back" link="<%=path%>/uz-admin/admin-user-mag" id="go-back"><i></i>返回</span></p>
	<div class="container">
		<div class="module">
			<p class="label">修改管理员密码</p>
         	<div>
         		<p class="input-uz"><input type="password" id="oldPassword" value="" placeholder="请输入原密码"/></p>
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
        		<p><input class="submit" type="submit" id="post" value="提交" /></p>
         	<p class="tip">修改成功后，请用新密码登录</p>
        	</div>
	</div>
</div>
</body>
</html>