<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="head" class="w cf">
    <div class="left">
        <p class="logo"><a href="<%=path%>"><img src="<%=staticPath%>/img/logo-blank.png" /></a></p>
        <div id="nav">
            <ul class="cf">
                <li><span><a href="<%=path%>/pl">悠哉工作</a></span></li>
             	<li><span><a href="<%=path%>/ul">悠哉人才</a></span></li>
                <li><span><a href="<%=path%>/s/how">如何使用</a></span></li>
            </ul>
        </div>
    </div>
    <div class="right">
    	<ul>
           <c:choose>
				<c:when test="${empty onlineUser or onlineUser.userId == 0}">
					 <li><a href="<%=path%>/login">登录</a></li>
			         <li><a href="<%=path%>/register">注册</a></li>
				</c:when> 
				<c:otherwise>
					<li><img src="<%=imagePath%>/user/<c:out value="${onlineUser.idImage}" />"></li>
					<li class="me"><span><a><c:out value="${onlineUser.username}" /></a></span><i></i>
						<dl>
							<dd><a href="<%=path%>/uc/profile">个人主页</a></dd>
							<dd><a href="<%=path%>/uc/project">我的项目</a></dd>
							<dd><a href="<%=path%>/uc/account">我的账户</a></dd>
							<dd><a href="<%=path%>/logout">退出登录</a></dd>
						</dl>
					</li>
				</c:otherwise>
			</c:choose>
        </ul>
    </div>
</div>