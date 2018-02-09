<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="description" content="奥丁财富网是全国领先的第三方财富管理平台，专注于高端财富管理和资产配置，为高净值用户提供一对一的财富管理方案，为用户财富保值，增值。"/>
<meta name="keywords" content="奥丁财富，奥丁金融，私募基金，阳光私募，信托，股权投资"/>
<meta name="copyright" content="本页版权归奥丁财富所有，All rights reserved" />
<meta name="author" content="奥丁财富 www.odincf.com" />
<meta name="robots" content="all" />
<title>奥丁财富 - 新闻系统 - 后台管理</title>
<%@ include file="admin-common.jsp"%>
</head>
<body>
<div id="main">
	<div>
	   <form name="search-form" action="<%=path%>/od-admin-news/operation-manage"  method="post">
	   <input type="hidden" name="process" value="search" />
		<span class="log-title">操作日志</span>
		<span class="keywordInput"><input type="text" name="keywords" value="${keywords}"placeholder="请输入关键词"/></span>
		<span class="query-time">查询时间：</span>
		<span class="timeInput"><input type="text" id="searchStartDate" placeholder="请输入开始时间" name="searchStartDate" readonly="true" class="laydate-icon" onclick="laydate()" value="<c:out value="${searchStartDate}" />" /></span>
		<i>—</i>
		<span class="timeInput"><input type="text" id="searchEndDate"  placeholder="请输入结束时间"  name="searchEndDate" class="laydate-icon" onclick="laydate()" value="<c:out value="${searchEndDate}" />"  /></span>
		<span class="submitInput"><input type="submit" value="查询" /></span>
		</form>
	</div>
	<div class="log-bg">
		<table>
			<tbody>
				<tr>
					<th class="log-id">ID</th>
					<th class="user-name">用户</th>
					<th class="operation-time">操作时间</th>
					<th class="operation-container">操作内容</th>
					<th class="detail-message">详细信息</th>
				</tr>
				<c:forEach items="${operationList}" var="operation" varStatus="status">
				    <tr id="msgid-${operation.id}">
				    <td><c:out value="${operation.id}" /></td>
				    <td><c:out value="${operation.createdUserName}" /></td>
				    <td><c:out value="${operation.createdTime}" /></td>
				    <td><c:out value="${operation.content}" /></td>
				    <td class="detail">${operation.detail}</td>
				    </tr>
				</c:forEach>
			</tbody>
		</table>
		<p class="log-all">所有操作记录(共 ${operationAmount} 条)</p>
		<div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div>
	</div>
</div>
</body>
</html>