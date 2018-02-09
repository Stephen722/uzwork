<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(function(){
	$(".popup .cancelBtn, .popup .close").click(function(){
		popup_close();
	});
	
	$(".popup .okBtn").click(function(){
		review_post();
	});
	
	$(".popup input[type=radio]").click(function(){
		var val = $(this).val();
		if(val == 2) {
			$(".popup #reason").attr("disabled", false);
		}
		else {
			$(".popup #reason").val("");
			$(".popup #reason").attr("disabled", true);
		}
	});
	
	$(".popup textarea").focus(function(){
		$(this).css("border-color", "#117bd8");
	}).blur(function(){
		$(this).css("border-color", "#e0e0e0");
	});
});
function review_post(){
	$.ajax({
		url:path+"/uz-admin/user-mag",
		data:{
			process:"review_post",
			curUserId: $("#curUserId").val(),
			curStatusId: $("#curStatusId").val(),
			curReviewId: $("#curReviewId").val(),
			status: $("input[type=radio]:checked").val(),
			comment: $("#comment").val(),
			reason: $("#reason").val()
		},
		dataType:"json",
		type:"post",
		error: function(){dialog_box("请求失败");},
		success: function(data){
			dialog_box(data.message);
			if(data.success) {
				popup_close();
				setTimeout("location.href = path + '/uz-admin/user-mag?process=review_list'", 2500);
			}
		},
		complete: function(data) {
		}
	});	
}
</script>
<div id="mask"></div>
<div id="reviewDIV" class="popup">
	<div class="head">
		<p class="title">用户审核</p>
		<p class="close"><i></i></p>
	</div>
	<input type="hidden" id="curStatusId" value="" />
	<input type="hidden" id="curUserId" value="" />
	<input type="hidden" id="curReviewId" value="" />
	<div class="tip">请选择审核结果：
		<input type="radio" name="status" value="1">通过
		<input type="radio" name="status" value="2">不通过
		<input type="radio" name="status" value="0">保持现状
		<p>备注：<textarea id="comment" placeholder="请输入备注（200字以内）"></textarea></p>
		<p>原因：<textarea id="reason" placeholder='请输入"不通过"的原因（200字以内），该原因将以消息形式发送给用户' disabled></textarea></p>
	</div>
	<div class="pbtn">
		<button class="cancelBtn">取消</button>
		<button class="okBtn">确定</button>
	</div>
</div>