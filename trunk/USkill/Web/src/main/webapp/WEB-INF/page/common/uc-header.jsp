<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="head">
    <div class="wd clearfix">
        <div class="left">
            <p class="logo"><a href="<%=path%>"><img src="<%=staticPath%>/img/logo-blank.png" /></a></p>
            <div id="nav">
                <ul class="clearfix">
                    <li><span><a href="<%=path%>">首页</a></span></li>
	                <li><span><a href="<%=path%>/uc/profile">个人主页</a></span></li>
                    <li><span><a href="<%=path%>/uc/project">我的项目</a></span></li>
                    <li><span><a href="<%=path%>/uc/account">我的账户</a></span></li>
                    <li><span class="unread-msg"><a href="<%=path%>/uc/message">消息</a>
                    <c:if test="${!empty onlineUser and onlineUser.userId > 0 and onlineUser.msgNumber > 0}">
                    	<c:choose>
	                        <c:when test="${onlineUser.msgNumber gt 9}">
	                            <em class="msg-ful"></em>
	                        </c:when> 
	                        <c:otherwise>
	                        	<em><c:out value="${onlineUser.msgNumber}" /></em>
	                        </c:otherwise>
                    	</c:choose> 
                    </c:if> 
                    </span></li>
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
								<dd><a href="<%=path%>/logout">退出</a></dd>
							</dl>
						</li>
					</c:otherwise>
				</c:choose>
            </ul>
        </div>
    </div>
</div>