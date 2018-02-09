<!DOCTYPE html><%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>悠哉工作 - 最专业的自由职业者工作平台</title>
<meta name="description" content="专业的自由职业者在线工作平台，悠哉工作(UZWORK)">
<meta name="keyword" content="自由职业者，在线工作，uzwork，freelancer，online work">
<%@ include file="common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=staticPath%>/style/index.css" />
</head>
<body>
<div id="ind-head">
	<%@ include file="common/header.jsp"%>
    <div class="ban-text">
       	<ul>
           	<li class="t1">用专业技能创造自由生活</li>
               <li class="t2">最专业的自由职业者工作平台</li>
               <c:choose>
               	<c:when test="${empty onlineUser or onlineUser.userId == 0}">
               		<li class="btn"><a href="<%=path%>/login">成为自由职业者</a></li>
               	</c:when>
               	<c:otherwise>
               		<li class="btn"><a href="<%=path%>/pp">免费发布项目</a></li>
               	</c:otherwise>
               </c:choose>
         </ul>
    </div>
</div>
<div class="fff wd">
   <div id="choose">
       <div class="item">
            <ul>
               <li><i class="us"></i><p><span>找人才完成项目</span></p></li>
               <li><p><em>你要啥，都有专业的人帮你</em></p></li>
               <li><p><a href="<%=path%>/pp" target="_blank" class="btn">发布项目</a></p></li>
           	</ul>
       </div>
       <div class="item">
           <ul>
               <li><i class="wk"></i><p><span>找项目随时工作</span></p></li>
               <li><p><em>你在哪，都有工作在等着你</em></p></li>
               <li><p><a href="<%=path%>/pl" class="btn">寻找工作</a></p></li>
           </ul>
       </div>
   </div>
   <div id="category">
       <ul>
           <li class="clearfix">
               <p>
                   <a href="<%=path%>/cat/software">
                       <i class="nav1"></i>
                       <em>软件开发</em>
                   </a>
               </p>
               <p>
                   <a href="<%=path%>/cat/design">
                       <i class="nav2"></i>
                       <em>创意设计</em>
                   </a>
               </p>
               <p>
                   <a href="<%=path%>/cat/marketing">
                       <i class="nav3"></i>
                       <em>推广运营</em>
                   </a>
               </p>
               <p>
                   <a href="<%=path%>/cat/writing">
                       <i class="nav4"></i>
                       <em>写作翻译</em>
                   </a>
               </p>
           </li>
       </ul>
   </div>
   <p id="uzwork">“悠哉工作”是什么？</p>
</div>
<div class="eee">
	<div class="wd des">
    	<div class="zy clearfix">
            <div class="left">
                <ul class="textlist">
                	<li><em>“悠哉工作”是最专业的自由职业者工作平台</em></li>
                    <li><p><i></i><span>专业的人才和项目对接机制</span></p></li>
                    <li><p><i></i><span>高效的项目管理流程和标准的验收步骤</span></p></li>
                    <li><p><i></i><span>安全的资金担保和完善的信用制度</span></p></li>
                </ul>
            </div>
            <div class="right">
                <p class="img"></p>
            </div>
        </div>
    </div>
</div>
<div class="fff">
	<div class="wd des">
    	<div class="rw clearfix">
        	<div class="left">
            	<p class="img"></p>
            </div>
            <div class="right">
            	<ul class="textlist">
                	<li><em>有项目要做？</em></li>
                    <li><p><i></i><span>提交真实详细的项目需求</span></p></li>
                    <li><p><i></i><span>选择符合要求的悠哉人才</span></p></li>
                    <li style="padding-top:20px;"><p><span>一对一服务，随时跟进项目进度，专业和信用保障，提高效率，降低成本</span></p></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="eee">
	<div class="wd des">
    	<div class="gz clearfix">
            <div class="left">
                <ul class="textlist">
                	<li><em>要找工作？</em></li>
                    <li><p><i></i><span>填写真实的个人信息和专业技能</span></p></li>
                    <li><p><i></i><span>申请可承接的项目</span></p></li>
                    <li style="padding-top:20px;"><p><span>点对点对接，自由安排工作时间，依靠专业技能，创作更多价值</span></p></li>
                </ul>
            </div>
            <div class="right">
                <p class="img"></p>
            </div>
        </div>
    </div>
</div>
 
<div class="fff">
	<div class="wd des">
    	<div class="sm clearfix">
        	<div class="left">
            	<p class="img"></p>
            </div>
            <div class="right">
            	<ul class="textlist">
                	<li><em>我们要做什么？</em></li>
                    <li style="padding-top:20px;"><p><span>我们致力于“用专业技能创造自由生活”，充分发挥每个人的专业特长，创造更多的社会价值，让越来越多的人通过“悠哉工作”实现职业梦想，享受自由生活。</span></p></li>
                    <li style="padding-top:20px;"><p><span>“新工作”时代已悄然开始，“雇佣”时代将慢慢落幕。每一个独立主动的人都将落入一个全新的工作时代。不需要走进办公室，却可以随时随地的工作。没有公司与员工，只有平台和个人；告别传统公司，节约社会成本；依靠专业技能，创造更多价值；未来将是一个自由开放的劳动市场，每个人都在平等状态中，依靠技能和信用，遵守契约和规则，快乐地工作，自由地生活。</span></p></li>
                    <li style="padding-top:20px;"><p><span>每个人都有冲破枷锁获得自由的机会，这是一场新工作时代的革命。</span></p></li>
                </ul>
            </div>
        </div>
    </div>
</div>  
<div id="bot-link">
	<span><em>想成为自由职业者</em><a href="<%=path%>/register" class="btn1">免费注册</a></span>
    <span><em>寻找精英人才？</em><a href="<%=path%>/pp" target="_blank" class="btn2">发布项目</a></span>
</div> 
<%@ include file="common/footer.jsp"%>
</body>
</html>