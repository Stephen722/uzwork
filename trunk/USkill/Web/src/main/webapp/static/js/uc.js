
$(function(){
	adjustSlideHeight();
	
	// select the navigation item
	$(".uc #slide li").each(function(){
		if($(this).find("a")) {
			var href = $(this).find("a").attr("href");
			var url = window.location.href;
			if(url.endWith(href)) {
				$(this).addClass("sel");
				return false;
			}
		}
	});
	
	$(".uc .cat li").click(function(){
		$t = $(this);
		$t.addClass("sel");
		$t.siblings().removeClass("sel");
		var id = $t.attr("id");
		var boxId = "#"+id+"-box";
		$(boxId).show();
		$(boxId).siblings().hide();
	});
	
	// User Account Begin
	$("#uc-acc .cat li").click(function(){
		var id = $(this).attr("id");
		if("mag" == id) {
			getAccount();
		}
		else if("trade" == id) {
			getTransaction();
		}
	});
	
	$("#uc-acc #cashAmount").blur(function(){
		var totalCash = parseInt($("#cash-box #cashTotal").text());
		var t = $(this);
		var val = t.val();
		if(val.length > 0) {
			if(!isInt(val)){
				t.errorShow("请输入有效的提现金额");
			}
			else if(totalCash <= 0) {
				t.errorShow("您的账户当前金额为零，不能提取");
			}
			else if(val > totalCash){
				t.errorShow("请输入不超过账户金额的提现金额");
			}
		}
	});
	
	$("#uc-acc #realName").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("真实姓名不能为空");
		}
		else if(!isRealName(val)){
			t.errorShow("请输入您的真实姓名");
		}
	});
	
	$("#uc-acc #cash-box #cashPost").click(function(){
		if(!isInt($("#cashAmount").val())){
			$("#cashAmount").errorShow("请输入有效的提现金额");
			return false;
		}
		var cashTotal = parseInt($("#cashTotal").text());
		var cashAmount = parseInt($("#cashAmount").val());
		if(cashTotal <= 0) {
			$("#cashAmount").errorShow("您的账户当前金额为零，不能提取");
			return false;
		}
		else if(cashAmount <= 0) {
			$("#cashAmount").errorShow("请输入有效的提现金额");
			return false;
		}
		else if(cashAmount > cashTotal) {
			$("#cashAmount").errorShow("请输入不超过账户金额的提现金额");
			return false;
		}
		else {
			applyForCash();
		}
	});
	
	$("#uc-acc #payAccount").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("账号不能为空");
		}
		else if(val.length < 3 || val.length > 60){
			t.errorShow("请输入您的支付宝账号");
		}
	});
	
	$("#uc-acc #mag-box #accountPost").click(function(){
		var realName = $("#realName").val();
		var payAccount = $("#payAccount").val();
		if(realName == "") {
			$("#realName").errorShow("真实姓名不能为空");
			return false;
		}
		else if(!isRealName(realName)){
			$("#realName").errorShow("请输入您的真实姓名");
			return false;
		}
		if(payAccount == "") {
			$("#payAccount").errorShow("账号不能为空");
			return false;
		}
		else if(payAccount.length < 3 || payAccount.length > 60){
			$("#payAccount").errorShow("请输入您的支付宝账号");
			return false;
		}
		
		$.ajax({
			url: path + "/uc/account",
			data: {process: "saveAccount", realName: realName, account: payAccount},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				UZDialogFade(data.message);
				if(data.success) {
					$("#mag-box .read .realName span").text(realName);
					$("#mag-box .read .payAccount span").text(payAccount);
					$("#mag-box .read").show();
					$("#mag-box .edit").hide();
				}
			},
			beforeSend: function () {
				loading(true);
				$("#accountPost").val("提交中...");
				$("#accountPost").attr("disabled", true);
		    },
			complete:function(){
				loading(false);
				$("#accountPost").val("提交");
				$("#accountPost").attr("disabled", false);
			}
		});
	});
	
	$("#uc-acc #mag-box #accountUpdate").click(function(){
		var realName = $("#mag-box .read .realName span").text();
		var payAccount = $("#mag-box .read .payAccount span").text();
		$("#realName").val(realName);
		$("#payAccount").val(payAccount);
		$("#mag-box .read").hide();
		$("#mag-box .edit").show();
		$("#mag-box #accountCancel").show();
	});
	
	$("#uc-acc #mag-box #accountCancel").click(function(){
		getAccount();
	});
	// User Account End
	
	// User Message Begin
	$("#uc-msg .cat li").click(function(){
		var id = $(this).attr("id");
		if("msg" == id) {
			getMessage();
		}
	});
	
	// set pagination when loading message page
	var msgTotal = $("#uc-msg #msg-box #msgTotal").val();
	if(msgTotal > 0) {
		$("#uc-msg #msg-box .page").html(pageHtml(msgTotal, 8, 1, "msgPagination"));
	}

	$("#uc-msg #msg-box .msg-list dt .title").click(function(){
		$(this).siblings(".time").children("b").click();
	});
	
	$("#uc-msg #msg-box .msg-list dt b").click(function(){
		collapseMessage(this);
	});
	
	$("#uc-msg #msg-box .msg-list dd .del").click(function(){
		deleteMessageConfirm($(this).attr("id"));
	});
	// User Message End
});
function adjustSlideHeight() {
	var slideHT = $(".uc #slide").height();
	var mainHT = $(".uc .main").height();
	if(mainHT >= 600) {
		$(".uc #slide").height(mainHT);
	}
}

function getAmount() {
	$.ajax({
		url: path + "/uc/account",
		data: {process: "getAmount"},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				var amount = 0;
				if(data.value > 0) {
					amount = data.value;
				}
				$("#uc-acc #cashTotal").text(amount);
			}
		}
	});
}
function getAccount() {
	$.ajax({
		url: path + "/uc/account",
		data: {process: "getAccount"},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				if(data.value.realName) {
					$("#mag-box .read .realName span").text(data.value.realName);
					$("#mag-box .read .payAccount span").text(data.value.account);
					$("#mag-box .read").show();
					$("#mag-box .edit").hide();
				}
				else {
					$("#mag-box .read").hide();
					$("#mag-box .edit").show();
					$("#mag-box #accountCancel").hide();
				}
			}
		}
	});
}
function applyForCash() {
	var cashTotal = parseInt($("#cashTotal").text());
	var cashAmount = parseInt($("#cashAmount").val());
	$.ajax({
		url: path + "/uc/account",
		data: {process: "applyForCash", cashAmount: cashAmount},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			UZDialogFade(data.message);
			if(data.success) {
				$("#cashTotal").text(cashTotal - cashAmount);
				$("#cashAmount").val("");
			}
		},
		beforeSend: function () {
			loading(true);
			$("#cashPost").val("提交中...");
			$("#cashPost").attr("disabled", true);
	    },
		complete:function(){
			loading(false);
			$("#cashPost").val("提现");
			$("#cashPost").attr("disabled", false);
		}
	});
}
function getTransaction() {
	var page = 1;
	if($("#transPage").val()) {
		page = $("#transPage").val();
	}
	$.ajax({
		url: path + "/uc/account",
		data: {process: "getTransaction", page: page},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				var len = data.value.results.length;
				var transHtml = "<input type='hidden' id='transPage' value=1 />";
				if(len > 0) {
					transHtml += "<table><thead><tr><th class='desc'>描述</th><th class='amount'>金额</th><th class='time'>时间</th></tr></thead><tbody>";
					for(var i = 0; i < len; i++) {
						var trans = data.value.results[i];
						transHtml += "<tr><td class='dc'>"+trans.description+"</td><td class='at'>"+trans.amount+"</td><td>"+trans.createdTime+"</td></tr>";
					}
					
					transHtml += "<tr><td colspan='3'><p class='page'>" + pageHtml(data.value.total, 8, page, "transPagination")+"</p></td></tr>";
					transHtml += "</tbody></table>";
				}
				else {
					transHtml += "<p class='empty'>暂无现金交易记录</p>"
				}
				$("#trade-box").html(transHtml);
			}
		}
	});
}
function transPagination(page){
	$("#transPage").val(page);
	getTransaction();
}
function collapseMessage(t) {
	var $_parent = $(t).parent().parent();
	var $_next = $_parent.next();
	// reset all dd
	$_parent.siblings("dd").hide();
	$_parent.siblings("dt").removeClass("dtNoBTM");
	$_parent.siblings("dt").find("b").text("展开").removeClass("collapse").addClass("expand");
	
	// show current dd and remove the bottom border
	var newClass = "collapse";
	var curClass = $(t).attr("class");
	var newText = "收起";
	if("collapse" == curClass) {
		newClass = "expand";
		newText = "展开";
		$_next.hide();
		$_parent.removeClass("dtNoBTM");
	}
	else {
		$_next.show();
		$_parent.addClass("dtNoBTM");
		
		// update read flag
		var id = $_parent.attr("id");
		if($_parent.hasClass("unread")) {
			readMessage(id);
		}
	}
	$(t).text(newText).removeClass(curClass).addClass(newClass);
}
function getMessage() {
	var page = 1;
	if($("#msgPage").val()) {
		page = $("#msgPage").val();
	}
	$.ajax({
		url: path + "/uc/message",
		data: {process: "getMessage", page: page},
		dataType: "JSON",
		type: "POST",
		async: true,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success) {
				var msgHtml = "<input type='hidden' id='msgPage' value='1' />";
				msgHtml += "<div class='msg-list'>";
				var len = data.value.results.length;
				if(len > 0) {
					msgHtml += "<dl>";
					for(var i = 0; i < len; i++) {
						var msg = data.value.results[i];
						if(msg.readB == 0) {
							msgHtml += "<dt id='"+msg.id+"' class='unread'>";
						}
						else {
							msgHtml += "<dt id='"+msg.id+"' >";
						}
						msgHtml += "<em></em><span class='title'>"+msg.title+"</span><span class='time'><i>"+msg.createdTime+"</i><b class='expand'>展开</b></span></dt>";
						msgHtml += "<dd><b class='avt'></b><span>"+msg.content+"</span><b id='msgId"+msg.id+"' class='del'></b></dd>";
					}
					msgHtml += "</dl>";
					msgHtml += "<p class='page'>" + pageHtml(data.value.total, 8, page, "msgPagination")+"</p>";
				}
				else {
					msgHtml += "<p class='empty'>暂无消息</p>"
				}
				msgHtml += "</div>";
				$("#msg-box").html(msgHtml);
				
				$("#uc-msg #msg-box .msg-list dt .title").click(function(){
					$(this).siblings(".time").children("b").click();
				});
				$("#uc-msg #msg-box .msg-list dt b").click(function(){
					collapseMessage(this);
				});
				
				$("#uc-msg #msg-box .msg-list dd .del").click(function(){
					deleteMessageConfirm($(this).attr("id"));
				});
			}
		}
	});
}
function msgPagination(page){
	$("#msgPage").val(page);
	getMessage();
}
function deleteMessageConfirm(idStr) {
	UZDialogConfirm("确定删除消息？","删除消息 - 提示信息", deleteMessage, idStr);
}
function deleteMessage(idStr) {
	var id = 0;
	if(idStr.length > 5) {
		id = parseInt(idStr.substr(5));
	}
	if(id > 0) {
		$.ajax({
			url: path + "/uc/message",
			data: {process: "delete", messageId: id},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				UZDialogFade(data.message);
				if(data.success) {
					// refresh the message list if the page (not the first page) has only one message
					var $_parent_dd = $("#"+idStr).parent();
					if($_parent_dd.siblings("dt").length <= 1) {
						getMessage();
					}
					else {
						var $_parent_dt = $_parent_dd.prev();
						$_parent_dd.remove();
						$_parent_dt.remove();
					}
				}
			}
		});
	}
	else {
		UZDialogFade("请求失败");
	}
}
function readMessage(id){
	if(id > 0) {
		$.ajax({
			url: path + "/uc/message",
			data: {process: "read", messageId: id},
			dataType: "JSON",
			type: "POST",
			async: true,
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				if(data.success) {
					// remove unread
					var $_parent = $("#"+id);
					if($_parent.hasClass("unread")) {
						$_parent.removeClass("unread");
					}
					// update unread massage number
					var numStr = $("#msg .unread-msg em").text();
					if(numStr.length > 0) {
						var num = parseInt(numStr);
						if(num > 1) {
							$(".unread-msg em").text(num - 1);
						}
						else {
							$(".unread-msg em").remove();
						}
					}
				}
			}
		});
	}
	else {
		UZDialogFade("请求失败");
	}
}