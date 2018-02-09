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
	//CKEDITOR.instances.contentText.setData("Hello World!");
	$("#back-1").click(function(){
		var link=$(this).attr("link");
		window.parent.$("#indexiframe").attr("src",link);
   	});
	var selected=Geturlkye("categoryId");
	if(selected!=null){
		var index=selected-5;
		$(".changeTitle span").eq(index).addClass("selected");
	}
	$(".checked").click(function(){
		if($(this).find("i").is(":hidden")){ 
			checked=1;
			$(".ckd").val(1);
			$(".checked i").css({"display":"block"});
		}else{
			checked=0;
			$(".ckd").val(0);
			$(".checked i").hide();
		}
	});
	$("#status-type").click(function(){
		$("#show-status").fadeIn();
	});
	$("#show-status li").click(function(){
		var text=$(this).text();
		$("#status-type").text(text);
		$("#show-status").hide();
	});
	$(".industry-class").mouseleave(function(){
		$(".list-classes").hide();
	});
	
	$("#text1").click(function(){
		$("#list1").fadeIn();
	});
	
	$("#text2").click(function(){
		$("#list2").fadeIn();
	});
	
	$(".list-classes li").click(function(){
		var text=$(this).text();
		var val=$(this).attr("index");
		var t=$(this).parent()
		t.siblings(".hiddeninfo").val(val);
		t.siblings("span").text(text);
		t.hide();
	});
	

	
	$(".input-sub").click(function(){
		var v=true;
		var cdr=CKEDITOR.instances.contentText.getData();
		$("#textareaText").val(cdr);
		var style=$("#channel").val();
		var recStyle=$("#recommend-type").val();
		var title=$(".input-title").val();
		var brief=$("#brief").val();
		var number=$(".reading-input").val();
		var keyword=$("#key-word").val();
		var status=$("#statusType").val();
		var routeText=$("#RouteText").val();
		var telvd=/^\d+$/;
		var specialvd=/^[\d|a-z|A-Z]*$/;
		if(!style){
			dialog_box("请选择行业类别");
			return false;
		}
		if(!recStyle){
			dialog_box("请选择推荐类型");
			return false;
		}
		if(!title){
			dialog_box("标题不能为空");
			return false;
		}
		if(!brief){
			dialog_box("简介不能为空");
			return false;
		}
		if(!$("#textareaText").val()){
			dialog_box("正文不能为空");
			return false;
		} 	
		if(!routeText){
			dialog_box("请输入选择封面图片");
			return false;
		}
		if(!number.match(telvd) || number.length > 9){
			dialog_box("请输入正确阅读量");
			v=false;
			return false;
		}
		if(!keyword){
			dialog_box("关键词不能为空 ");
			return false;
		}
		if(!status){
			dialog_box("请选择显示状态"); 
			return false;
		}
		$.each($(".input-corID"),function(a,b){
			var t=$(this).val();
			if(t!='' && !t.match(telvd)){
				dialog_box("请输入正确文章ID");
				v=false;
				return false;
			}
			else if(t.length>9) {
				dialog_box("请输入正确文章ID");
				v=false;
				return false;
			}
			var currentCategoryId = Geturlkye("categoryId");
			isValidArticle(t,currentCategoryId);
			if(!isValid) {
				if(currentCategoryId == 5) {
					dialog_box("关联文章只能关联行业新闻下的文章");
				}
				else if(currentCategoryId == 6) {
					dialog_box("关联文章只能关联奥丁观察下的文章");
				}
				else if(currentCategoryId == 7) {
					dialog_box("关联文章只能关联理财学堂下的文章");
				}
				v=false;
				return false;
			}
			
		});
		if(!v){
			return false;
		}
	});
	
	$("#RouteText").blur(function(){
		var imgurl=$("#RouteText").val();
		var success=0;
		if(imgurl!=""){
			var cdr=CKEDITOR.instances.contentText.getData();
			var textar=[];
			var n=0;
			cdr.replace(/src="(\S*)"/g,function(word){
				textar[n]=word.match(/src="(\S*)"/)[1];
				n++;
			});
			if(textar.length > 0){
				for(var i=0;i<textar.length;i++){
					var x=textar[i].split("/");
					var imgar=x[x.length-1];
					if(imgar==imgurl){
						$("#Mark-img img").attr("src",textar[i]);
						success=1;
					}
				}
			}
			if(success==0){
				var path = '<%=path%>';
				dialog_box("填写封面必须是编辑器里面上传的图片");
				$("#RouteText").val("");
				$("#Mark-img img").attr("src",path + "/public/image/img-white-image.png");
			}
		}
	});
	
	$(".recommendType").mouseleave(function(){
		$(".recommendType-list").hide();
	});
	$(".recommendType-list li").click(function(){
		if($(this).find("i").is(":hidden")){
			$(this).attr("ckd","1");
			$(this).find("i").css({"display":"block"});
		}else{
			$(this).find("i").hide();
			$(this).attr("ckd","0");
		}
	});
	$("#recommendType-Btn").click(function(){
		var ckd='';
		var n=0;
		var text='';
		$.each($(".recommendList"),function(a,b){
			if($(this).attr("ckd")=="1"){
				ckd+=$(".recommendList").eq(a).attr("index");
				text=$(this).text();
			}
		});
		if(ckd!=''){
			switch (ckd)
			{
			    case "12":
			    	$("#recommend-type").val("5");
			    	$("#text2").text("本周/网站");
			    break;
			    case "13": 
			    	$("#recommend-type").val("6");
			    	$("#text2").text("本周/视野");
			    break;
			    case "23": 
			    	$("#recommend-type").val("7");
			    	$("#text2").text("网站/视野");
			    break;
			    case "123": 
			    	$("#recommend-type").val("8");
			    	$("#text2").text("本周/网站/视野");
			    break;
			    default: 
			    	$("#recommend-type").val(ckd);
			    	$("#text2").text(text);
				break;
			}
		}else{
			dialog_box("请选择推荐类型");
			return false;
		}
	});
	$(".recommendCancel").click(function(){
		$(".recommendList").not(this).attr("ckd","0").find("i").css({"display":"none"});
	});
	$(".recommendIndex").click(function(){
		$(".recommendCancel").attr("ckd","0").find("i").css({"display":"none"});
	});
	
})

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

function Geturlkye(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


//下面用于图片上传预览功能
function setImagePreview(avalue) {
	 
    var docObj = document.getElementById("Upfile");
    var imgObjPreview = document.getElementById("pvwImg");
    var divs = document.getElementById("pvw-img");
    var Upfile=$("#Upfile").val();

    var Imgsize=docObj.files.size || docObj.files[0].size;
    Imgsize=Number(Imgsize);
    if(Imgsize > 1024000){
    	dialog_box("请上传1M以内的图片");
        return false;
      }
    $("#RouteText").val(Upfile);
    if (docObj.files && docObj.files[0]) {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = '186px';
        imgObjPreview.style.height = '130px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
       imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
    } else {
        //IE下，使用滤镜
        docObj.select();
        var imgSrc = document.selection.createRange().text;
        var localImagId = document.getElementById("pvw-img");
        //必须设置初始大小
        localImagId.style.width = "100px";
        localImagId.style.height = "100px";
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        try {
            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
        } catch(e) {
            alert("您上传的图片格式不正确，请重新选择!");
            return false;
        }
        imgObjPreview.style.display = 'none';
        document.selection.empty();
    }
	return true;
}

</script>
</head>
<body>
<div id="main">
	<div class="changeTitle cfix">
		<p class="main-navigation">
			<span>信息审核</span>
		</p>
		<p class="get-back" link="<%=path%>/uzwork-admin-center/user-mag" id="back-1"><i></i>返回</p>
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