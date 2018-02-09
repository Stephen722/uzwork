<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 用户管理</title>
<%@ include file="admin-common.jsp"%>
<script>
var curUserId = 0;
$(function(){
	$(".deactivate").click(function(){
		curUserId = $(this).parent("ul").attr("id");
		popup("用户被锁定后将不能登录系统，确定锁定该用户？", "操作提示", changeStatus, 0);
	});
	$(".activate").click(function(){
		curUserId = $(this).parent("ul").attr("id");
		popup("用户被解锁后即可正常使用，确定解锁该用户？", "操作提示", changeStatus, 1);
	});
	
	$(".history").click(function(){
		var userId = $(this).parent("ul").attr("id");
		location.href = path + "/uz-admin/user-mag?process=review_history&userId="+userId;
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
			error: function(){alert("请求失败");},
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
	<p class="pageTitle cfix"><span>用户管理</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/user-mag"  method="post">
			注册时间：<span class="date"><input type="text" id="searchStartDate" placeholder="起始时间" name="searchStartDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchStartDate}" />" /></span>
			<i>—</i>
			<span class="date"><input type="text" id="searchEndDate"  placeholder="结束时间"  name="searchEndDate" readonly class="laydate-icon" onclick="laydate()" value="<c:out value="${searchEndDate}" />"  /></span>
			<span class="keyword"><input type="text" name="keywords" value="${keywords}" placeholder="请输入用户名/手机号"/></span>
			<span class="dropdown">认证：
				<select name="statusId">
					<option value='all' <c:if test="${empty statusId}">selected</c:if>>所有</option>
					<option value="0" <c:if test="${statusId eq 0}">selected</c:if>>未认证</option>
					<option value="1" <c:if test="${statusId eq 1}">selected</c:if>>待认证</option>
					<option value="2" <c:if test="${statusId eq 2}">selected</c:if>>已认证</option>
					<option value="3" <c:if test="${statusId eq 3}">selected</c:if>>未通过认证</option>
				</select>
			</span>
			<span class="dropdown">可用：
				<select name="activeB">
					<option value="all" <c:if test="${empty activeB}">selected</c:if>>所有</option>
					<option value="0" <c:if test="${activeB eq 0}">selected</c:if>>锁定</option>
					<option value="1" <c:if test="${activeB eq 1}">selected</c:if>>可用</option>
				</select>
			</span>
			<span class="submit"><input type="submit" value="查询" /></span>
		</form>
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">用户ID</th>
					<th class="td10">手机号</th>
					<th class="td10">用户名</th>
					<th class="td10">城市</th>
					<th class="td10">注册时间</th>
					<th class="td10">类型</th>
					<th class="td10">认证</th>
					<th class="td10">可用</th>
					<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr id="trId-${user.userId}">
						<td><c:out value="${user.userId}" /></td>
						<td><c:out value="${user.cellPhone}" /></td>
						<td class="a"><a href="<%=path%>/u/<c:out value="${user.userIdStr}" />" target="_blank"><c:out value="${user.username}" /></a></td>
						<td>
							<c:choose>
					    		<c:when test="${empty user.city}">N/A</c:when>
					    		<c:otherwise><c:out value="${user.city}" /></c:otherwise>
				    		</c:choose>
						</td>
						<td><c:out value="${user.createdTime}" /></td>
						<td><c:out value="${user.userType.name}" /></td>
						<td><c:out value="${user.status.name}" /></td>
						<td>
							<c:choose>
					    		<c:when test="${user.activeB == 1}">可用</c:when>
					    		<c:otherwise><b class="red">锁定</b></c:otherwise>
				    		</c:choose>
						</td>
						<td class="operation">
						    	<div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${user.userId}" class="option selecting"  >
					     			<li class="review"><a href="<%=path%>/u/<c:out value="${user.userIdStr}" />" target="_blank">查看</a></li>
					     			<li class="history">审核纪录</li>
					     			<c:if test="${user.status.code eq 'CHECKING'}">
								    		<li class="reward">审核</li>
								    </c:if>
									<c:choose>
								    		<c:when test="${user.activeB == 1}"><li class="deactivate">锁定</li></c:when>
								    		<c:otherwise><li class="activate">解锁</li></c:otherwise>
							    		</c:choose>
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
</body>
</html>