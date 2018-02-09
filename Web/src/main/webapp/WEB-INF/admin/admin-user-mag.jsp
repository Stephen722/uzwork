<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 管理员管理</title>
<%@ include file="admin-common.jsp"%>
<script>
var curAdminId = 0;
$(function(){
	$(".deactivate").click(function(){
		curAdminId = $(this).parent("ul").attr("id");
		popup("管理员被锁定后将不能登录系统，确定锁定？", "操作提示", changeStatus, 0);
	});
	$(".activate").click(function(){
		curAdminId = $(this).parent("ul").attr("id");
		popup("管理员被解锁后即可正常使用，确定解锁？", "操作提示", changeStatus, 1);
	});

	$(".delete").click(function(){
		var adminId = $(this).parent("ul").attr("id");
		popup("管理员被删除后将无法恢复，确定删除？", "操作提示", deleteAdminUser, adminId);
	});
	
});
function changeStatus(activeB){
	if(curAdminId > 0) {
		$.ajax({
			url:path+"/uz-admin/admin-user-mag",
			data:{
				process:"activate",
				adminId: curAdminId,
				activeB: activeB
			},
			dataType:"json",
			type:"post",
			error: function(){dialog_box("请求失败");},
			success: function(data){
				popup_close();
				if(data.success){
					if(activeB == 1) {
						dialog_box("管理员已被成功激活");
					}
					else {
						dialog_box("管理员已被成功锁定");
					}
					setTimeout("location.reload()", 2500);
				}
				else {
					dialog_box(data.message);	
				}
			},
			complete: function(data) {
				curAdminId = 0;
			}
		});	
	}
}
function deleteAdminUser(adminId){
	if(adminId > 0) {
		$.ajax({
			url:path+"/uz-admin/admin-user-mag",
			data:{
				process:"deleteAdminUser",
				adminId: adminId
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
	<p class="pageTitle cfix"><span>管理员管理</span></p>
	<div class="search">
		<form name="search-form" action="<%=path%>/uz-admin/admin-user-mag"  method="post">
			<span class="keyword"><input type="text" name="keywords" value="${keywords}" placeholder="请输入管理员名"/></span>
			<span class="submit"><input type="submit" value="查询" /></span>
			<span class="add"><a href="<%=path%>/uz-admin/admin-user-mag?process=add">新增管理员</a></span>
		</form>
	</div>
	<div class="container">
		<table>
			<thead>
				<tr>
					<th class="td5">管理员ID</th>
		      		<th class="td10">管理员名</th>
		      		<th class="td10">管理员类型</th>
		      		<th class="td10">创建时间</th>
		      		<th class="td10">最近登录IP</th>
		      		<th class="td10">最近登录时间</th>
		      		<th class="td5">登录次数</th>
		      		<th class="td5">当前状态</th>
			      	<th class="operation">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${adminList}" var="admin" varStatus="status">
					<tr id="trId-${admin.adminId}">
						<td><c:out value="${admin.adminId}" /></td>
						<td><c:out value="${admin.username}" /></td>
						<td>
							<c:choose>
						    		<c:when test="${admin.groupId == 0}">超级管理员</c:when>
						    		<c:otherwise>一般管理员</c:otherwise>
					    		</c:choose>
						</td>
						<td><c:out value="${admin.createdTime}" /></td>
					    <td><c:out value="${admin.loginIp}" /></td>
					    <td><c:out value="${admin.loginTime}" /></td>
					    <td><c:out value="${admin.logins}" /></td>
					    <td>
					    	<c:choose>
					    		<c:when test="${admin.activeB == 1}">激活</c:when>
					    		<c:otherwise><b class="red">锁定</b></c:otherwise>
					    	</c:choose>
					    </td>
						<td class="operation">
						    	<div>
					     		<p class="mag"><span class="sort-white">管理</span></p>
				     			<ul id="${admin.adminId}" class="option selecting"  >
							    	<c:choose>
							    		<c:when test="${admin.activeB == 1}"><li class="deactivate">锁定</li></c:when>
							    		<c:otherwise><li class="activate">解锁</li></c:otherwise>
							    	</c:choose>
							    	<li class="delete">删除</li>
								</ul>
							</div>	
					     </td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot><tr><td><td colspan="2" align="left">共有 ${totalAmount} 个管理员</td><td colspan="7"><div class="page"><c:out value="${pageInfo}" escapeXml="false"/></div></td></tr></tfoot>
		</table>
	</div>
</div>
</body>
</html>