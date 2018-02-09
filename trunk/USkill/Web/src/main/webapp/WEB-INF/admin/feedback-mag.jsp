<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 用户反馈</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$(".delete").click(function(){
		var feedbackId = $(this).parent("ul").attr("id");
		popup("反馈被删除后将无法恢复，确定删除？", "操作提示", deleteFeedback, feedbackId);
	});
});
function deleteFeedback(feedbackId){
	if(feedbackId > 0) {
		$.ajax({
			url:path+"/uz-admin/feedback-mag",
			data:{
				process:"delete",
				feedbackId: feedbackId
			},
			dataType:"json",
			type:"post",
			error: function(){dialog_box("请求失败");},
			success: function(data){
				popup_close();
				dialog_box(data.message);
				if(data.success){
					setTimeout("location.reload()", 2500);
				}
			}
		});	
	}
}
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>用户反馈</span></p>
	<div class="search">
		如果你想回复用户，可以在通知管理中发送短消息给该用户
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">ID</th>
					<th class="td5">用户ID</th>
					<th class="td10">联系方式</th>
					<th class="td60">反馈内容</th>
					<th class="td10">提交时间</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${feedbackList}" var="feedback" varStatus="status">
					<tr id="trId-${feedback.id}">
						<td><c:out value="${feedback.id}" /></td>
						<td>
							<c:choose>
					    		<c:when test="${empty feedback.userId}">N/A</c:when>
					    		<c:otherwise><c:out value="${feedback.userId}" /></c:otherwise>
				    		</c:choose>
						</td>
						<td>
							<c:choose>
					    		<c:when test="${empty feedback.contact}">N/A</c:when>
					    		<c:otherwise><c:out value="${feedback.contact}" /></c:otherwise>
				    		</c:choose>
						</td>
						<td class="tl"><c:out value="${feedback.content}" /></td>
						<td><c:out value="${feedback.createdTime}" /></td>
						<td class="operation">
						    <div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${feedback.id}" class="option selecting"  >
								    <li class="delete">删除</li>
								</ul>
							</div>	
					     </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 条反馈</td><td colspan="3"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
</body>
</html>