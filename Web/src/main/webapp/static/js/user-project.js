var curShowTabId = "";
$(function(){
	$("#uc-pro #wip-box .pro").each(function(){
		var projectId = $(this).attr("id");
		var statusCode = $(this).find(".hide-var i").text();
		var postBid = $(this).find(".hide-var b").text();
		var postBidText = "中标";
		var iconLabel = "发布";
		var postBidStatus = "bid-status";
		// set label before project title
		if(postBid.length > 0){
			if(postBid == 'post') {
				postBidText = "发布";
				iconLabel = "中标";
				postBidStatus = "post-status";
			}
			$(this).find(".info .title b").text("["+postBidText+"的项目]");
			$(this).find(".detail .rt .bidder i").text(iconLabel);
			$(this).find(".rt label").addClass(postBidStatus);
		}
		// set the tip information
		if(statusCode.length > 0) {
			var status = statusCode.toLowerCase();
			$(this).find(".detail .rt .next").html($("#"+postBidStatus).children("."+status).html());
			// bind click event of next step button
			$("#"+projectId+" .next .btn").click(function(){
				var btnPopupId = "#"+postBidStatus+"-"+status;
				// set project id for subsequent process
				$(btnPopupId).attr("pid", projectId);
				$(btnPopupId).UZDialog();
				var callback = $(btnPopupId).attr("fun");
				if($.isFunction(eval(callback))) {
					eval(callback + "("+projectId+")");
				}
				// bind submit function
				var submitFunction = $(btnPopupId).attr("fun-sub");
				if($.isFunction(eval(submitFunction))) {
					$(btnPopupId).find(".submit").click(function(){
						eval(submitFunction + "()");
					})
				}
			});
			
			if("RECRUIT" == statusCode) {
				// if the order is waiting for agreement/reject with recruit project, please show recruit0/2 div
				var orderAgreed = $(this).find(".order").attr("oa");
				if(orderAgreed == 0) {
					$(this).find(".detail .rt .next").html($("#"+postBidStatus).children(".recruit0").html());
					
					var userId = $(this).find(".bidder .bidderId").val();
					var orderId = $("#"+projectId+" .order").attr("id");
					$("#"+projectId+" .next .updateChoose").click(function(){
						$("#post-status-recruit").UZDialog();
						$(".bids-pop #submitBid").click(function(){
							return chooseBid();
						});
						
						// hide others, only show choose
						$(".bids-list-div").hide();
						$(".bids-list-sendMsg").hide();
						$(".bids-list-choose").show();
						
						// initialization
						var bidId = $("#"+projectId+" .order").attr("bid");
						var comment = $("#"+projectId+" .order .order-comment").val();
						var price = $("#"+projectId+" .order .dat .pr").text();
						var startDate = $("#"+projectId+" .order .dat .sd").text();
						var endDate = $("#"+projectId+" .order .dat .ed").text();
						$("#bids-userId").val(userId);
						$("#bids-projectId").val(projectId);
						$("#orderId").val(orderId);
						$(".bids-list-choose h3").text("修改项目订单信息");
						$(".bids-list-choose #price").val(price);
						$(".bids-list-choose #startDate").val(startDate);
						$(".bids-list-choose #endDate").val(endDate);
						$(".bids-list-choose #comment").val(comment);
						getBid(bidId);
					});
					$("#"+projectId+" .next .allBids").click(function(){
						$("#post-status-recruit").attr("pid", projectId);
						$("#post-status-recruit").UZDialog();
						getAllBidsForRecruit(projectId);
					});
					$("#"+projectId+" .next .reSendMsg").click(function(){
						$("#post-status-recruit").attr("pid", projectId);
						$("#post-status-recruit").UZDialog();
						$(".bids-pop #submitMsg").click(function(){
							return sendMsg();
						});
						
						// hide others, only show send msg
						$(".bids-list-div").hide();
						$(".bids-list-sendMsg").show();
						$(".bids-list-choose").hide();
						
						// initialization
						$("#bids-userId").val(userId);
						$("#bids-projectId").val(projectId);
						$("#orderId").val(orderId);
						$(".bids-list-sendMsg h3").text("发送消息给项目中标者");
						$(".bids-list-sendMsg .us-title").text($("#"+projectId+" .bidder b a").text());
						$(".bids-list-sendMsg .pro-title").text($("#"+projectId+" .title a").text());
					});
				}
				else if(orderAgreed == 2){
					$(this).find(".detail .rt .next").find("p:first").html("你选择的“投标者”拒绝了你的邀请，请选择其他“投标者”");
				}
				// if orderAgreed is 1, no need more special process 
			}
		}
		
		// set status step
		if("RECRUIT" == statusCode) {
			$(this).find(".step .step1").addClass("cur");	
		}
		else if("UNPAY" == statusCode) {
			$(this).find(".step .step1").addClass("light");	
			$(this).find(".step .step2").addClass("cur");	
		}
		else if("WIP" == statusCode) {
			$(this).find(".step .step1").addClass("light");	
			$(this).find(".step .step2").addClass("light");	
			$(this).find(".step .step3").addClass("cur");	
		}
		else if("ACCEPT" == statusCode) {
			$(this).find(".step .step1").addClass("light");	
			$(this).find(".step .step2").addClass("light");	
			$(this).find(".step .step3").addClass("light");	
			$(this).find(".step .step4").addClass("cur");	
		}
		else if("EVALUATE" == statusCode) {
			$(this).find(".step .step1").addClass("light");	
			$(this).find(".step .step2").addClass("light");	
			$(this).find(".step .step3").addClass("light");	
			$(this).find(".step .step4").addClass("light");	
			$(this).find(".step .step5").addClass("cur");	
		}
		
	});
	
	$("#uc-pro .cat li").click(function(){
		var id = $(this).attr("id");
		if(id != "wip") {
			curShowTabId = id;
			getProject(id);
		}
		else { // only for WIP
			adjustSlideHeight();	
			curShowTabId = "";
		}
	});

	$(document).on("focus", "input[type=text], textarea", function(){
		$(this).addClass("input-focus").removeClass("error-bd");
	});
	$(document).on("blur", "input[type=text], textarea", function(){
		$(this).removeClass("input-focus");
	});
});

function pagination(page){
	if(curShowTabId.length > 0) {
		$("#"+curShowTabId+"Page").val(page);
		getProject(curShowTabId);
	}
}
function getProject(id) {
	var boxId = "#" + id + "-box";
	var page = 1;
	if($("#" + id + "Page").length > 0) {
		page = $("#" + id + "Page").val();
	};
	$.ajax({
		url: path + "/uc/project",
		data: {process: "getProject", type: id, page: page},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				var ptHtml = "<input type='hidden' id='" + id + "Page' value=1 />";
				var len = data.value.results.length;
				if(len > 0) {
					for(var i = 0; i < len; i++) {
						var project = data.value.results[i];
						var html = "<div class='pt' id='" + id + project.projectId+"'>";
						html += "<p class='title'><b>"+project.status+"</b><a href='"+path+"/p/"+project.projectIdStr+"' target='_blank'>"+project.title+"</a>";
						if(id == "post") {
							html += "<i><a href='javascript: projectCancelConfirm(\""+project.title+"\","+project.projectId+");'>取消</a></i>";
						}
						else if(id == "fav") {
							html += "<i><a href='javascript: favoriteDeleteConfirm(\""+project.title+"\","+project.projectId+");'>删除</a></i>";
						}
						html += "</p><p class='info'>预算：<i>"+project.budget+"</i>期限：<i>"+project.duration+"</i>类别：<b>"+project.category.name+"/"+project.subCategory.name+"</b>发布时间：<b>"+project.createdTime+"</b></p>";
						
						if(id == "bid") {
							html += "<p class='info'>我的报价：<i>"+project.price+"元</i>报价时间：<b>"+project.bidCreatedTime+"</b>我的建议：<b>"+project.proposal+"</b></p>";
						}
						
						html += "</div>";
						ptHtml += html;
					}
					ptHtml += "<p class='page'>" + pageHtml(data.value.total, 5, page, "pagination")+"</p>";
				}
				else {
					ptHtml += "<p class='empty'>暂无该类项目</p>"
				}
				$(boxId).html(ptHtml);
			}
			else {
				UZDialogFade(data.message);
			}
			// adjust slide height because of the main area has been changed
			adjustSlideHeight();
		}
	});
}
function projectCancelConfirm(title, id) {
	UZDialogConfirm("确定取消已发布项目？<br/>"+title,"取消已发布产品 - 提示信息", projectCancel, id);
}
function projectCancel(id) {
	UZDialogFade("项目已取消");
//	$.ajax({
//		url: path + "/uc/project",
//		data: {process: "favDelete", projectId: id},
//		dataType: "JSON",
//		type: "POST",
//		async: true,
//		error: function(){UZDialogFade("请求失败");},
//		success: function(data){
//			UZDialogFade(data.message);
//			if(data.success) {
//				$("#fav"+id).remove();
//			}
//		}
//	});
}
function favoriteDeleteConfirm(title, id) {
	UZDialogConfirm("确定删除该收藏？<br/>"+title,"删除收藏 - 提示信息", favoriteDelete, id);
}
function favoriteDelete(id) {
	$.ajax({
		url: path + "/uc/project",
		data: {process: "favDelete", projectId: id},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			UZDialogFade(data.message);
			if(data.success) {
				$("#fav"+id).remove();
			}
		}
	});
}

function getAllBidsForRecruit(projectId) {
	// init
	$(".bids-list-div").show();
	$(".bids-list-sendMsg").hide();
	$(".bids-list-choose").hide();
	
	var page = 1;
	if($("#page"+projectId).length > 0) {
		page = $("#page"+projectId).val();
	};
	$.ajax({
		url: path + "/uc/project",
		data: {process: "getAllBids", projectId: projectId, page: page},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				var bidsHtml = "";
				var len = data.value.results.length;
				if(len > 0) {
					bidsHtml = "<input type='hidden' id='page" + projectId + "' value=1 />";
					bidsHtml +=	"<table><thead><tr><th>投标者</th><th>投标时间</th><th>报价(元)</th><th>建议</th><th>操作</th></tr></thead>";
					for(var i = 0; i < len; i++) {
						var bid = data.value.results[i];
						var html = "<tr><td class='u' title="+bid.username+"><a href='"+path+"/u/"+bid.userIdStr+"' target='_blank'><img src="+ imagePath + "/user/"+bid.idImage+" /></a></td>";
						html += "<td class='t'>"+bid.createdTime+"</td><td class='p' title="+bid.price+">"+bid.price+"</td><td class='pr' title='"+bid.proposal+"'>"+bid.proposal+"</td><td class='op' id='"+bid.userId+"'><b class='sendMsg'>发消息</b><i></i><b class='choose'>选择</b></td>";
						html += "</tr>";
						bidsHtml += html;
					}
					bidsHtml += "</table><p class='page'>" + pageHtml(data.value.total, 4, page, "getAllBidspagination")+"</p>";
				}
				else {
					bidsHtml += "<p class='empty'>暂无投标者</p>"
				}
				$(".bids-list").html(bidsHtml);
				$(".bids-list table .pr").each(function(){
					var maxwidth = 80;
					var allText = $(this).text();
					if($(this).text().length > maxwidth){
						$(this).text(allText.substring(0, maxwidth));
						$(this).html($(this).html()+"...");
					}
					
					//$(this).html($(this).html().replace(/\r\n|\n/g, "<br>"));
				});
				
				$("#bids-projectId").val(projectId);
				// checking already choose or not
				chooseBidVerify();
				
				
				// bind click event for sendMsg and choose
				$(".bids-list .sendMsg").click(function(){
					var alreadyChooseBidder = $("#already-choose-bidder").val();
					if(alreadyChooseBidder.length <= 0) {
						var userId = $(this).parent().attr("id");
						var username = $(this).parent().siblings(".u").attr("title");
						$("#bids-userId").val(userId);
						
						$(".bids-list-sendMsg .us-title").text(username);
						$(".bids-list-sendMsg .pro-title").text($("#"+projectId+" .title a").text());
						
						$(".bids-list-div").hide();
						$(".bids-list-sendMsg").show();
						$(".bids-list-choose").hide();
					}
					else {
						UZDialogFade("你已经选择了投标者“"+alreadyChooseBidder+"”，请耐心等待TA的确认。");
						$("#post-status-recruit .bids-list-div .tip").html("你已经选择了投标者“"+alreadyChooseBidder+"”，请耐心等待TA的确认。").css("color", "#ff4200");
					}
				});
				$(".bids-list .choose").click(function(){
					// not allow to choose multi bidder
					var alreadyChooseBidder = $("#already-choose-bidder").val();
					if(alreadyChooseBidder.length <= 0) {
						var userId = $(this).parent().attr("id");
						var username = $(this).parent().siblings(".u").attr("title");
						var price = $(this).parent().siblings(".p").attr("title");
						var time = $(this).parent().siblings(".t").text();
						$("#bids-userId").val(userId);
						$(".bids-list-choose .us-title").text(username);
						$(".bids-list-choose .price-title").text(price+"元");
						$(".bids-list-choose .time-title").text("("+time+")");
						
						$(".bids-list-div").hide();
						$(".bids-list-sendMsg").hide();
						$(".bids-list-choose").show();
					}
					else {
						UZDialogFade("你已经选择了投标者“"+alreadyChooseBidder+"”，请耐心等待TA的确认。");
						$("#post-status-recruit .bids-list-div .tip").html("你已经选择了投标者“"+alreadyChooseBidder+"”，请耐心等待TA的确认。").css("color", "#ff4200");
					}
					
				});
			}
			else {
				UZDialogFade(data.message);
			}
		},
		complete: function(){
			$(".bids-pop #price").blur(function(){
				var t = $(this);
				var val = t.val();
				if(!isInt(val)) {
					UZDialogFade("项目定价必须为整数");
					t.addClass("error-bd");
				}
			});
			
			$(".bids-pop #submitMsg").click(function(){
				return sendMsg();
			});
			$(".bids-pop #submitBid").click(function(){
				return chooseBid();
			});
		}
	});
}
function getAllBidspagination(page){
	var projectId = $("#post-status-recruit").attr("pid");
	$("#page"+projectId).val(page);
	getAllBidsForRecruit(projectId);
}
function bidsReturn() {
	$(".bids-list-div").show();
	$(".bids-list-sendMsg").hide();
	$(".bids-list-choose").hide();
	//reset
	$(".bids-pop input[type=text], textarea").val("");
	$(".bids-pop input[type=text], textarea").removeClass("error-bd").removeClass("input-focus");
	// if the current process is updateChoose or reSendMsg, close div
	if($("#orderId").val() > 0) {
		UZDialogClose();
		$("orderId").val(0); // reset orderId, it only active when click updateChoose and reSendMsg buttons
	}
}
function sendMsg(){
	var message = $(".bids-pop #message").val();
	if(message.length < 5 || message.length > 1000) {
		$(".bids-pop #message").addClass("error-bd");
		UZDialogFade("请输入5-1000字的消息内容");
		return false;
	}
	$(".bids-pop #submitMsg").val("提交中...");
	$(".bids-pop #submitMsg").attr("disabled", true);
	$.ajax({
		url: path + "/uc/project",
		data: {
			process: "sendMsg", 
			bidder: $("#bids-userId").val(), 
			projectId: $("#bids-projectId").val(), 
			message: message
		},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			UZDialogFade(data.message);
			if(data.success) {
				bidsReturn();
			}
		},
	    complete: function(){
	    	$(".bids-pop #submitMsg").val("提交");
			$(".bids-pop #submitMsg").attr("disabled", false);
	    }
	});
}
function chooseBid() {
	var orderId = $(".bids-pop #orderId").val();
	var comment = $(".bids-pop #comment").val();
	var price = $(".bids-pop #price").val();
	var startDate = $(".bids-pop #startDate").val();
	var endDate = $(".bids-pop #endDate").val();

	if(!isInt(price)) {
		$(".bids-pop #price").addClass("error-bd");
		UZDialogFade("项目定价必须为整数");
		return false;
	}
	else if(startDate.length < 5) {
		$(".bids-pop #startDate").addClass("error-bd");
		UZDialogFade("选择项目开始日期");
		return false;
	}
	else if(endDate.length < 5) {
		$(".bids-pop #endDate").addClass("error-bd");
		UZDialogFade("选择项目完工日期");
		return false;
	}
	$(".bids-pop #submitBid").val("提交中...");
	$(".bids-pop #submitBid").attr("disabled", true);
	$.ajax({
		url: path + "/uc/project",
		data: {
			process: "chooseBid", 
			orderId: orderId,
			bidder: $("#bids-userId").val(), 
			projectId: $("#bids-projectId").val(),
			price: price,
			comment: comment, 
			startDate: startDate,
			endDate: endDate
		},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){
			UZDialogFade("请求失败");
			$(".bids-pop #submitBid").val("提交");
			$(".bids-pop #submitBid").attr("disabled", false);
		},
		success: function(data){
			UZDialogFade(data.message);
			$(".bids-pop #submitBid").val("提交");
			if(data.success) {
				setTimeout('$(location).attr("href", path+"/uc/project")', 2500);
//				var bidder = $(".bids-list-choose .us-title").text();
//				$("#already-choose-bidder").val(bidder);
//				$("#post-status-recruit .bids-list-div .tip").html("你已经选择了投标者“"+bidder+"”，请耐心等待TA的确认。").css("color", "#ff4200");
//				bidsReturn();
			}
			else {
				$(".bids-pop #submitBid").attr("disabled", false);
			}
		}
	});
}
function chooseBidVerify(){
	$.ajax({
		url: path + "/uc/project",
		data: {
			process: "chooseBidVerify", 
			projectId: $("#bids-projectId").val()
		},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.value.length > 0) {
				$("#already-choose-bidder").val(data.value);
				$("#post-status-recruit .bids-list-div .tip").html("你已经选择了投标者“"+data.value+"”，请耐心等待TA的确认。").css("color", "#ff4200");
			}
		}
	});
}
function getBid(bidId){
	$.ajax({
		url: path + "/uc/project",
		data: {
			process: "getBid", 
			bidId: bidId,
			projectId: $("#bids-projectId").val()
		},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.value) {
				$(".bids-list-choose .us-title").text(data.value.username);
				$(".bids-list-choose .price-title").text(data.value.price+"元");
				$(".bids-list-choose .time-title").text("("+data.value.createdTime+")");
			}
		}
	});
}
function postStatusAccept(){
	alert("postStatusAccept");
}