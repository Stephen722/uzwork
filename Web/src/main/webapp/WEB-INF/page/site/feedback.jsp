<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<title>悠哉工作 - 最专业的自由职业者工作平台</title><%@ include file="../common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=staticPath%>/style/site.css?v=123" />
</head>
<body>
<%@ include file="../common/header.jsp"%>
<div class="banner ban-post-success">
    <div class="wd">用专业技能创造自由生活</div>
</div>
<div class="feedback wd">
	<form id="feedbackForm" method="post" action="<%=path%>/s/feedback">
       	<input type="hidden" name="process" value="post" />
       	<input type="hidden" name="UZWorkToken" id="UZWorkToken" value="<c:out value="${UZWorkToken}"/>" />
		<p>意见反馈<em>您的宝贵建议是我们不断改进的动力！</em></p><br/>
		<div class="input-uz">
			<textarea id="suggestion" name="suggestion" placeholder="请输入10-400字的建议内容"></textarea>
		</div>
		<p><input type="text" name="contact" class="contact" value="" maxlength="20"/><em>如果方便，请在此留下您的联系方式</em></p>
		<span class="error"><c:out value="${message}" /></span>
		<input type="button" id="post" value="提交反馈"  />
		<span class="error-msg"></span>
	</form>
</div>
<%@ include file="../common/footer.jsp"%>
<script type="text/javascript">
var isSuggestionValid = false;
$(function(){
	var error = $("#feedbackForm .error").html();
	if(error.length > 0) {
		$("#feedbackForm #suggestion").attr("disabled", true);
		$("#feedbackForm #post").css("cursor","default").attr("disabled", true);
	}
	$("#feedbackForm #suggestion").blur(function(){
		if($(this).val().length > 0) {
			suggestionValidation();
			$(".login-error").text("");
		}
	});

	// Submit
	$("#feedbackForm #post").click(function() {
		suggestionValidation();
		if(isSuggestionValid) {
			$(this).val("提交中...");
			$(this).attr("disabled", true);
			$("#feedbackForm").submit();
		}
		else {
			return false;
		}
	});
});

function suggestionValidation() {
	var t = $("#suggestion");
	var val = t.val();
	if(val != "" && val.length >=10 && val.length <= 400){
		isSuggestionValid = true;
	}
	else {
		t.errorShow("请输入10-400字的建议内容");
		isSuggestionValid = false;
	}
}
</script>
</body>
</html>