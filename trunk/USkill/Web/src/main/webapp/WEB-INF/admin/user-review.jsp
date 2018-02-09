<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>悠哉工作 － 管理后台 － 用户管理</title>
<%@ include file="../views/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-base.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/admin/style/admin-style.css" />
<script type="text/javascript" src="<%=path%>/admin/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/admin/js/main.js"></script>
<link rel="stylesheet" type="text/css" href="<%=staticPath%>/style/user.css" />
<script>
$(function(){
	$(".invite").click(function(){
		$("#mask, #reviewDIV").fadeIn(500);
		$("#curUserId").val($(".history").attr("id"));
		$("#curStatusId").val($(this).attr("sid"));
		$("#curReviewId").val($(this).attr("rid"));
	});
	
	$(".history").click(function(){
		var userId = $(this).attr("id");
		location.href = path + "/uz-admin/user-mag?process=review_history&userId="+userId;
	});
});
</script>
</head>
<body>
<div id="main">
	<p class="pageTitle cfix"><span>用户管理</span><span class="back" link="<%=path%>/uz-admin/user-mag?process=review_list" id="go-back"><i></i>返回</span></p>
    <div id="info">
    	<div id="detail">
            <ul class="clearfix">
                <li class="icon">
                    <p><img src="<%=imagePath%>/user/<c:out value="${user.idImage}" />" /></p>
                    <span><c:out value="${user.status.name}"/>会员</span>
                </li>
                <li class="desc">
                    <b class="title"><c:out value="${user.username}" /><span><c:out value="${user.title}" /></span></b>
                    <div class="adv">
                    	<i><c:out value="${user.city}" /></i>
                    	<c:forEach items="${user.category}" var="category">
	            			<i><c:out value="${category.name}"/></i>
	            		</c:forEach>
                    </div>
                    <div class="me">
                        <em>关于我</em>
                    </div>
                    <div class="ct htmlRN"><c:out value="${user.brief}" escapeXml="false"/></div>
                    <div class="me">
                        <em>擅长技能</em>
                    </div>
                    <div class="skill">
                   	 	<c:forEach items="${user.skill}" var="skill" varStatus="status">
	            			<i class="sk<c:out value="${status.index}"/>"><c:out value="${skill.name}"/></i>
	            		</c:forEach>
                    </div>
                </li>
                <li class="score clearfix">
                    <p class="invite" sid="<c:out value='${user.status.id}' />" rid="<c:out value='${user.reviewId}' />">审核</p><p class="history" id='<c:out value="${user.userId}" />' style="margin-top: 20px;text-align: right;color: #1669B2;font-size: 15px;cursor:pointer;">查看审核纪录</p>
                </li>
            </ul>
        </div>
        <div id="works">
        	<div id="title">
            	<i></i>
                <span>作品案例</span>
                <b></b>
            </div>
        	<div id="list">
        		<c:forEach items="${user.individualWorks}" var="work">
	            	<div class="item">
	                    <p class="img">
		                    <c:if test="${!empty work.image}">
		                    	<img src="<%=imagePath%>/works/<c:out value="${work.image}" />"  />
		                    </c:if>
	                    </p>
	                    <div class="text">
	                        <p>
	                            <b><c:out value="${work.title}"/></b>
	                            <i><c:out value="${work.responsibility}"/></i>
	                            <span><c:out value="${work.description}" escapeXml="false"/></span>
	                        </p>
	                        <c:if test="${!empty work.link}">
	                        	<a href="<c:out value="${work.link}"/>" target="_blank">作品链接</a>
	                        </c:if>
	                    </div>
	                </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="user-review-part.jsp"%>
</body>
</html>