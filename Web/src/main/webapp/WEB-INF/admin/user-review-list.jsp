<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 用户管理</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$(".history").click(function(){
		var userId = $(this).siblings(".review").attr("uid");
		location.href = path + "/uz-admin/user-mag?process=review_history&userId="+userId;
	});
	
	$(".operation .review").click(function(){
		$("#mask, #reviewDIV").fadeIn(500);
		$("#curReviewId").val($(this).parent("ul").attr("id"));
		$("#curUserId").val($(this).attr("uid"));
		$("#curStatusId").val($(this).attr("sid"));
	});
});
function changeStatus(activeB){
	if(curUserId > 0) {
		$.ajax({
			url:path+"/uz-admin/user-mag",
			data:{
				process:"activate",
				userId: curUserId,
				activeB: activeB
			},
			dataType:"json",
			type:"post",
			error: function(){dialog_box("请求失败");},
			success: function(data){
				popup_close();
				if(activeB == 1) {
					dialog_box("用户已被成功激活");
				}
				else {
					dialog_box("用户已被成功锁定");
				}
				if(data.success){
					setTimeout("location.reload()", 2500);
				}
			},
			complete: function(data) {
				curUserId = 0;
			}
		});	
	}
}
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>用户审核管理</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/user-mag"  method="post">
			<input type="hidden" name="process" value="review" />
			提交时间：<span class="date"><input type="text" id="searchStartDate" placeholder="起始时间" name="searchStartDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchStartDate}" />" /></span>
			<i>—</i>
			<span class="date"><input type="text" id="searchEndDate"  placeholder="结束时间"  name="searchEndDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchEndDate}" />"  /></span>
			<span class="keyword"><input type="text" name="keywords" value="${keywords}" placeholder="请输入用户名"/></span>
			<span class="submit"><input type="submit" value="查询" /></span>
		</form>
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">审核ID</th>
					<th class="td10">手机号</th>
					<th class="td10">用户名</th>
					<th class="td20">头衔</th>
					<th class="td10">城市</th>
					<th class="td10">提交时间</th>
					<th class="td10">认证</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${reviewList}" var="review" varStatus="status">
					<tr id="trId-${review.reviewId}">
						<td><c:out value="${review.reviewId}" /></td>
						<td><c:out value="${review.cellPhone}" /></td>
						<td class="a"><a href="<%=path%>/uz-admin/user-mag?process=review&userId=<c:out value="${review.userId}" />"><c:out value="${review.username}" /></a></td>
						<td class="tl"><c:out value="${review.title}" /></td>
						<td>
							<c:choose>
						    		<c:when test="${empty review.city}">N/A</c:when>
						    		<c:otherwise><c:out value="${review.city}" /></c:otherwise>
					    		</c:choose>
						</td>
						<td><c:out value="${review.createdTime}" /></td>
						<td><c:out value="${review.status.name}" /></td>
						<td class="operation">
						    	<div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${review.reviewId}" class="option selecting"  >
					     			<li><a href="<%=path%>/uz-admin/user-mag?process=review&userId=<c:out value="${review.userId}" />">查看</a></li>
								    	<li class="review" uid="<c:out value='${review.userId}' />" sid="<c:out value='${review.status.id}' />">审核</li>
								    	<li class="history">审核纪录</li>
								</ul>
							</div>	
					     </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 个用户</td><td colspan="6"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
<%@ include file="user-review-part.jsp"%>
</body>
</html>