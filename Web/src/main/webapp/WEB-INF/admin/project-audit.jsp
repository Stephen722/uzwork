<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="description" content="悠哉工作 - 最专业的自由职业者工作平台."/>
<meta name="keywords" content="悠哉工作 - 最专业的自由职业者工作平台."/>
<meta name="copyright" content="本页版权归悠哉所有，All rights reserved" />
<meta name="author" content="悠哉工作 -  www.uzwork.com" />
<meta name="robots" content="all" />
<title>悠哉工作 - 最专业的自由职业者工作平台</title>
<%@ include file="admin-common.jsp"%>
<script>
var isValid = true;
$(function(){

});

function userAudit(userId,status) {
	$.ajax({
		url:path()+"/uzwork-admin-center/user-info-audit",
		async: false,
		data:{
			userId:userId,
			status:status
		},
		dataType:"json",
		type:"post",
		error: function(){alert("请求失败");},
		success: function(data){
			if(data.success){
				var path = '<%=path%>';
				location = path + '/uzwork-admin-center/user-mag'; 
			}else{
				alert(data.message);
			}
		},
		complete:function(){
		}
	});
}
</script>
</head>
<body>
<div id="main">
	<div class="changeTitle cfix">
		<p class="main-navigation">
			<span>信息审核</span>
		</p>
		<p class="get-back" link="<%=path%>/uz-admin/user-mag" id="back-1"><i></i>返回</p>
	</div>
	<form id="audit-form" name="audit-form" action="<%=path%>/uzwork-admin-center/user-mag?process=auditUserInfo" enctype="multipart/form-data" method="post">
		<div class="user-audit">
			<div class="headerline">
				<p> 
				<span style="display:-moz-inline-box; display:inline-block;width:70px"> ID</span> <span><input type="text" name="userId" readOnly="true" value="${user.userId}"/> </span>
				<span style="width:20px"> 姓名</span> <span><input type="text" name="username" readOnly="true" value="${user.username}"/></span>
				<span style="width:20px" > 性别</span> <span><input type="text" name="userType"  readOnly="true" value="男"/></span>
				<span style="width:20px"> 真实姓名</span> <span> <input type="text" name="realName" readOnly="true" value="${user.realName}"/></span>
				 </p>
				 <br/>
				<p> 
				<span style="display:-moz-inline-box; display:inline-block;width:70px"> 手机号 </span> <span><input type="text" name="cellPhone" readOnly="true" value="${user.cellPhone}"/></span>
				<span style="width:20px"> 类型</span> <span><input type="text" name="userType"  readOnly="true" value="${user.userType.name}"/></span>
				<span style="width:20px"> 邮箱 </span> <span><input type="text" name="email" readOnly="true" value="${user.email}"/> </span>
				<span style="width:20px"> 身份证号</span> <span> <input type="text" name="idCard" readOnly="true" value="${user.idCard}"/></span>
				 </p>
				 <br/>
				 <p> 
				<span style="display:-moz-inline-box; display:inline-block;width:70px"> 所在城市</span> <span> <input type="text" name="city" readOnly="true" value="${user.city}"/></span>
				<span style="width:20px"> 工作年限</span> <span><input type="text" name="userType"  readOnly="true" value="${user.workingYears}"/>年</span>
				 </p>
				 <br/>
				 <p><span>职业类型</span>
				 <c:forEach items="${user.category}" var="userCategory" varStatus="status">
				 <span><input type="text" name="userCategory"  readOnly="true" value="${userCategory.name}"/></span>
				 </c:forEach>
				 </p>
				 <br/>
				 <p><span>擅长技能</span>
				 <c:forEach items="${user.skill}" var="userSkill" varStatus="status">
				 <span><input type="text" name="userSkill"  readOnly="true" value="${userSkill.name}"/></span>
				 </c:forEach>
				 </p>
				<div class="brief-introduction">
					个人简介<p class="details"><textarea id="brief" name="brief">${user.brief}</textarea></p>
				</div>
				<div class="brief-introduction">
					教育简介<p class="details"><textarea id="brief" name="brief">${user.brief}</textarea></p>
				</div>
				<div class="brief-introduction">
					工作简介<p class="details"><textarea id="brief" name="brief">${user.brief}</textarea></p>
				</div>
				<p class="main-text">作品简介</p>
			</div>
			<div class="submitbtnDiv">
				<p class="submitBtn"><input class="input-sub" type="button" onclick="userAudit(${user.userId},'QUALIFIED');" value="审核通过" /></p>
				<p class="submitBtn"><input class="input-sub" type="button" onclick="userAudit(${user.userId},'UNQUALIFIED');"value="审核未通过" /></p>
			</div>
		</div>
	</form>
</div>
</body>
</html>