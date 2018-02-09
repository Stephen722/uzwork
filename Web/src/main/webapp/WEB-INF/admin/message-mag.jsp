<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 通知管理</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$(".delete").click(function(){
		var messageId = $(this).parent("ul").attr("id");
		popup("消息被删除后将无法恢复，确定删除？", "操作提示", deleteMessage, messageId);
	});


	$(".pointer").click(function(){
		var phone = $(this).attr("tid");
		popup(phone, "指定用户");
	});
});
function deleteMessage(messageId){
	if(messageId > 0) {
		$.ajax({
			url:path+"/uz-admin/message-mag",
			data:{
				process:"deleteMessage",
				messageId: messageId
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
	<p class="pageTitle cfix"><span>通知管理</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/message-mag"  method="post">
			<span class="keyword"><input type="text" name="keywords" value="${keywords}" placeholder="请输入通知标题"/></span>
			<span class="submit"><input type="submit" value="查询" /></span>
			<span class="add"><a href="<%=path%>/uz-admin/message-mag?process=add">发送通知</a></span>
		</form>
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">ID</th>
					<th class="td10">发布人</th>
					<th class="td30">标题</th>
					<th class="td10">类型</th>
					<th class="td10">发布时间</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${messageList}" var="message" varStatus="status">
					<tr id="trId-${message.id}">
						<td><c:out value="${message.id}" /></td>
						<td><c:out value="${message.adminUsername}" /></td>
						<td class="tl"><c:out value="${message.title}" /></td>
						<c:choose>
				    		<c:when test="${message.destination == 0}"><td>所有客户</td></c:when>
				    		<c:otherwise><td class="red pointer" tid="<c:out value="${message.cellPhone}" />">指定客户</td></c:otherwise>
			    		</c:choose>
						<td><c:out value="${message.createdTime}" /></td>
						<td class="operation">
						    <div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${message.id}" class="option selecting"  >
								    	<li class="delete">删除</li>
								</ul>
							</div>	
					    </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 条通知</td><td colspan="4"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
</body>
</html>