<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 项目审核</title>
<%@ include file="../views/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-base.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-style.css" />
<script type="text/javascript" src="<%=path%>/admin/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/admin/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="<%=staticPath%>/style/project.css" />
<script>
$(function(){
	$("#bid").click(function(){
		$("#curProjectId").val($(".fav").attr("id"));
		$("#curStatusId").val($(this).attr("sid"));
		$("#mask, #reviewDIV").fadeIn(500);
	});
	
	$(".fav").click(function(){
		var projectId = $(this).attr("id");
		location.href = path + "/uz-admin/project-mag?process=review_history&projectId="+projectId;
	});
});

</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>项目审核管理</span><span class="back" link="<%=path%>/uz-admin/project-mag?process=review_list" id="go-back"><i></i>返回</span></p>
	<div id="pj" class="clearfix">
    	<div id="detail">
    		<input type="hidden" id="projectId" value="<c:out value="${project.projectIdStr}" />"/>
    		<input type="hidden" id="myProject" value="<c:out value="${myProject}" />"/>
    		<input type="hidden" id="myStatus" value="<c:out value="${onlineUser.status.code}" />"/>
    		<div class="info">
	    		<span><c:out value="${project.title}"/><em id="<c:out value="${project.status.code}"/>"><c:out value="${project.status.name}"/></em></span>
	    		<dl>
	    			<dt>项目类型：</dt><dd><a href="<%=path%>/cat/<c:out value="${fn:toLowerCase(project.category.code)}" />"><c:out value="${project.category.name}"/>/<c:out value="${project.subCategory.name}"/></a></dd>
	    			<dt>项目预算：</dt><dd><c:out value="${project.budget.name}"/></dd>
	    			<dt>项目周期：</dt><dd><c:out value="${project.duration.name}"/></dd>
	    			<dt>发布时间：</dt><dd><c:out value="${project.createdTime}"/></dd>
	    		</dl>
    		</div>
    		<div class="desc">
    			<span>项目描述</span>
    			<div class="htmlRN"><c:out value="${project.description}" escapeXml="false"/></div>
    		</div>
    	</div>
    	<div id="sider">
    		<input type="button" id="bid" value="审核" sid="<c:out value='${project.status.id}' />"/>
    		<p class="fav" id='<c:out value="${project.projectId}" />'><b>查看审核纪录</b></p><br/><br/>
    	</div>
    </div>
</div>
<%@ include file="project-review-part.jsp"%>
</body>
</html>