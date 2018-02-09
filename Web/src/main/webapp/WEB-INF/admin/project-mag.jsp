<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 项目管理</title>
<%@ include file="admin-common.jsp"%>
<script>
$(function(){
	$(".history").click(function(){
		var projectId = $(this).parent("ul").attr("id");
		location.href = path + "/uz-admin/project-mag?process=review_history&projectId="+projectId;
	});
});
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>项目管理</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/project-mag"  method="post">
			发布时间：<span class="date"><input type="text" id="searchStartDate" placeholder="起始时间" name="searchStartDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchStartDate}" />" /></span>
			<i>—</i>
			<span class="date"><input type="text" id="searchEndDate"  placeholder="结束时间"  name="searchEndDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchEndDate}" />"  /></span>
			<span class="keyword"><input type="text" name="keywords" value="${keywords}"placeholder="请输入项目名称"/></span>
			<span class="dropdown">状态：
					<select name="statusId">
						<option value='all' <c:if test="${empty statusId}">selected</c:if>>所有</option>
						<option value="0">草稿</option>
						<option value="1" <c:if test="${statusId eq '1'}">selected</c:if>>待审核</option>
						<option value="2" <c:if test="${statusId eq '2'}">selected</c:if>>未通过审核</option>
						<option value="3" <c:if test="${statusId eq '3'}">selected</c:if>>已取消</option>
						<option value="5" <c:if test="${statusId eq '5'}">selected</c:if>>招募中</option>
						<option value="6" <c:if test="${statusId eq '6'}">selected</c:if>>待付款</option>
						<option value="7" <c:if test="${statusId eq '7'}">selected</c:if>>工作中</option>
						<option value="8" <c:if test="${statusId eq '8'}">selected</c:if>>待验收</option>
						<option value="9" <c:if test="${statusId eq '9'}">selected</c:if>>待评价</option>
						<option value="10" <c:if test="${statusId eq '10'}">selected</c:if>>已完成</option>
					</select>
			</span>
			<span class="submit"><input type="submit" value="查询" /></span>
		</form>
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">ID</th>
					<th class="td5">发布者ID</th>
					<th class="td30">名称</th>
					<th class="td10">发布时间</th>
					<th class="td10">预算</th>
					<th class="td10">周期</th>
					<th class="td10">状态</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projectList}" var="project" varStatus="status">
					<tr id="trId-${project.projectId}">
						<td><c:out value="${project.projectId}" /></td>
						<td><c:out value="${project.userId}" /></td>
						<td class="tl a"><a href="<%=path%>/p/<c:out value="${project.projectIdStr}" />" target="_blank"><c:out value="${project.title}" /></a></td>
						<td><c:out value="${project.createdTime}" /></td>
						<td><c:out value="${project.budget.name}" /></td>
						<td><c:out value="${project.duration.name}" /></td>
						<td><c:out value="${project.status.name}" /></td>
						<td class="operation">
						    	<div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${project.projectId}" class="option selecting"  >
					     			<li><a href="<%=path%>/p/<c:out value="${project.projectIdStr}" />" target="_blank">查看</a></li>
					     			<li class="history">审核纪录</li>
								</ul>
							</div>	
					     </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 个项目</td><td colspan="5"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
</body>
</html>