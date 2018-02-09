$(function(){
	$(".operation .mag").click(function(){
		$(this).siblings(".selecting").show();
	});
	$(".operation .selecting li").click(function(){
		$(".operation .selecting").hide();
	});
	$(".operation div").mouseleave(function(){
		$(".operation .selecting").hide();
	});
	$("#go-back").click(function(){
		var link=$(this).attr("link");
		window.parent.$("#index-iframe").attr("src",link);
   	});
});
var dialog_box_index=0;
function dialog_box(boxtext)
{
	dialog_box_index++;
	var wh=$(window).height();
	var ww=$(window).width();
	var html="<div class='dialog-box dialog-box"+dialog_box_index+"'><p><span class='dialog_box-bg'></span><span class='dialog_box-text'><a>"+boxtext+"</a></span></p></div>";
	$("body").append(html);
	$(".dialog-box"+dialog_box_index+"").animate({opacity:1},500,function()
	{
		var t=$(this);
		setTimeout(function()
		{
			t.animate({opacity:0},1000);
		},1500);
		setTimeout(function()
		{
			t.remove();
		},3000);
	});
};
function popup(message, title, callback, parm, w, h) {
	if($("#mask").length > 0){
		$("#mask").each(function(){
			this.remove();
		});
	}
	if($(".popup").length > 0){
		$(".popup").each(function(){
			this.remove();
		});
	}
	
	var dTitle = "Confirmation";
	if(typeof(title) != "undefined") {
		dTitle = title;
	}
	var newHtml = "<div id='mask'></div><div class='popup'>";
		newHtml += "<div class='head'><p class='title'>"+title+"</p><p class='close'><i></i></p></div>";
		newHtml += "<div class='tip'>"+message+"</div>";

	if(typeof(callback) == "undefined") {
		newHtml += "<div class='pbtn'><button class='cancelBtn'>关闭</button></div>";
	}
	else {
		newHtml += "<div class='pbtn'><button class='cancelBtn'>取消</button><button class='okBtn' id=''>确定</button></div>";
	}
		
	$("body").append(newHtml);
	
	$(".popup .cancelBtn").click(function(){
		popup_close();
	});
	$(".popup .close").click(function(){
		popup_close();
	});
	$(".popup .okBtn").on("click", function(){
		callback(parm);
	});
	$("#mask,.popup").fadeIn(500);
}
function popup_close() {
	$("#mask,.popup").fadeOut(500);
}