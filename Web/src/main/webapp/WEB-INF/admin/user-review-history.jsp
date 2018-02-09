<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 用户审核纪录</title>
<%@ include file="admin-common.jsp"%>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>用户审核纪录</span><span class="back" link="<%=path%>/uz-admin/user-mag?process=review_list" id="go-back"><i></i>返回</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/user-mag"  method="post">
			<input type="hidden" name="process" value="review_history" />
			审核时间：<span class="date"><input type="text" id="searchStartDate" placeholder="起始时间" name="searchStartDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchStartDate}" />" /></span>
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
					<th class="td5">记录ID</th>
					<th class="td5">手机号</th>
					<th class="td10">用户名</th>
					<th class="td10">管理员</th>
					<th class="td10">审核时间</th>
					<th class="td10">初始状态</th>
					<th class="td10">提交状态</th>
					<th class="td20">备注</th>
					<th class="td20">原因</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${historyList}" var="history" varStatus="status">
					<tr id="trId-${history.id}">
						<td><c:out value="${history.id}" /></td>
						<td><c:out value="${history.cellPhone}" /></td>
						<td class="a"><a href="<%=path%>/uz-admin/user-mag?process=review_history&userId=<c:out value='${history.userId}' />"><c:out value="${history.username}" /></a></td>
						<td><c:out value="${history.adminUsername}" /></td>
						<td><c:out value="${history.createdTime}" /></td>
						<td><c:out value="${history.fromStatus}" /></td>
						<td><c:out value="${history.toStatus}" /></td>
						<td class="tl"><c:out value="${history.comment}" /></td>
						<td class="tl"><c:out value="${history.reason}" /></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 条审核纪录</td><td colspan="6"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
</body>
</html>