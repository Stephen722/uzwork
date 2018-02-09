<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>华纳众金  - 通知管理</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$("#title").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("通知标题不能为空");
		}
		else if(val.length < 3 || val.length > 60){
			t.errorShow("请输入3-60字的通知标题");
		}
	});
	$("#message").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("通知内容不能为空");
		}
		else if(val.length < 10 || val.length > 1000){
			t.errorShow("请输入10-1000字的通知内容");
		}
	});

	$("input[type=radio]").click(function(){
		var val = $(this).val();
		if(val == 1) {
			$("#cellPhone").parent().show();
		}
		else {
			$("#cellPhone").parent().hide();
			$("#cellPhone").parent().siblings(".error-msg").text("").hide();
		}
	});

	$("#cellPhone").blur(function(){
		var t = $(this);
		var val = trimStr(t.val());
		if(val == "") {
			t.errorShow("请输入指定用户的手机号");
		}
		else if(val.length < 11){
			t.errorShow("请输入合法的手机号");
		}
		else {
			var phone = val.split("\n");
			if(phone.length > 20) {
				t.errorShow("指定用户数不能超过20个，当前指定用户数："+phone.length);
			}
			else {
				for(var i = 0;i < phone.length;i++){
					if(!iscellPhone(trimStr(phone[i]))) {
						t.errorShow("第"+(i + 1)+"行的手机号不正确，请纠正");
						break;
					}
				}
			}
		}
	});
	
	$(".submit").click(function(){
		var title = $("#title").val();
		var message = $("#message").val();
		var destination = $(".choose input:radio:checked").val();
		var cellPhone = $("#cellPhone").val();
		if(title == "") {
			$("#title").errorShow("通知标题不能为空");
			return false;
		}
		else if(title.length < 3 || title.length > 60){
			$("#title").errorShow("请输入3-60字的通知标题");
			return false;
		}
		
		if(message == "") {
			$("#message").errorShow("通知内容不能为空");
			return false;
		}
		else if(message.length < 10 || message.length > 1000){
			$("#message").errorShow("请输入10-1000字的通知内容");
			return false;
		}
		if(destination == 1) {
			var cellPhone = trimStr($("#cellPhone").val());
			if(cellPhone == "") {
				$("#cellPhone").errorShow("请输入指定用户的手机号");
				return false;
			}
			else if(cellPhone.length < 11){
				$("#cellPhone").errorShow("请输入合法的手机号");
				return false;
			}
			else {
				var phone = cellPhone.split("\n");
				if(phone.length > 20) {
					t.errorShow("指定用户数不能超过20个，当前指定用户数："+phone.length);
					return false;
				}
				else {
					var allRight = true;
					for(var i = 0;i < phone.length;i++){
						if(!iscellPhone(trimStr(phone[i]))) {
							$("#cellPhone").errorShow("第"+(i + 1)+"行的手机号不正确，请纠正");
							allRight = false;
							break;
						}
					}
					if(!allRight) {
						return false;
					} 
				}
			}
		}
		
		$.ajax({
			url: path + "/uz-admin/message-mag",
			data: {process: "sendMsg", title: title, message: message, destination: destination, cellPhone: cellPhone},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				dialog_box(data.message);
				$(".submit").val("提交");
				if(data.success) {
					setTimeout('$(location).attr("href", path+"/uz-admin/message-mag")', 2500);
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
	<p class="pageTitle cfix"><span>发送新通知</span><span class="back" link="<%=path%>/uz-admin/message-mag" id="go-back"><i></i>返回</span></p>
	<div class="container">
		<div class="module msg-add">
			<p class="label">发送新通知</p>
			<div class="title">
				<p class="input-uz"><input type="text" id="title" value="" placeholder="请输入通知标题"/></p>
				<p class="error-msg"></p>
        	</div>
        	<div class="message">
         		<p class="input-uz"><textarea id="message" placeholder="请输入通知内容"></textarea></p>
        		<p class="error-msg"></p>
       		</div>
        	<div class="choose">
				<p class="dest"><input type="radio" name="destination" value="0" checked>发给所有用户<input type="radio" name="destination" value="1">发给指定用户</p>
				<p class="input-uz"><textarea id="cellPhone" placeholder="指定用户最多20个，且每行只能输入一个手机号"></textarea></p>
				<p class="error-msg"></p>
         	</div>
       		<p><input type="submit" class="submit" value="提交" /></p>
       		<p class="tip">指定用户最多20个，且每行只能输入一个手机号</p>
		</div>
	</div>
</div>
</body>
</html>