var category;
$(function(){
	loadProject();
	getCategory();
	var categoryCode = $("#categoryCode").val();
	var categoryName = "悠哉工作";
	if(categoryCode.length > 0) {
		categoryName = $("#"+categoryCode).text();
		$("#filter .list1 #"+categoryCode).addClass("sel");
		$("#filter .list1 span b").text(categoryName);
	}
	$(".banner .cat b").text(categoryName);
	
	$("#filter .list1 dl dd").click(function() {
		$(this).parent().fadeOut(200);
		$(this).siblings().removeClass("sel");
		$(this).addClass("sel");
		var catCode = $(this).attr("id");
		var txt = $(this).text();
		$("#filter .list1 span b").text(txt);
		$(".banner .cat b").text(txt);
		$("#categoryCode").val(catCode);
		$("#curPage").val(1);
		setSubCategory(catCode);
		loadProject();
	});
	$("#filter .list2 dl dd").click(function() {
		$(this).parent().fadeOut(200);
		$(this).siblings().removeClass("sel");
		$(this).addClass("sel");
		$("#filter .list2 span b").text($(this).text());
		$("#durationCode").val($(this).attr("id"));
		$("#curPage").val(1);
		loadProject();
	});
	$("#filter .list3 span").click(function() {
		$("#categoryCode").val("");
		$("#durationCode").val("");
		$("#curPage").val(1);
		$("#filter .list1 span b").text("项目类型");
		$("#filter .list1 dl dd").removeClass("sel");
		$("#filter .list2 span b").text("项目期限");
		$("#filter .list2 dl dd").removeClass("sel");
		$(".banner .cat b").text("悠哉工作");
		$(".banner .sub ul").html("<li>软件开发<i>\\</i></li><li>创意设计<i>\\</i></li><li>推广运营<i>\\</i></li><li>写作翻译</li>");
		loadProject();
	});
});
function loadProject() {
	var category = $("#categoryCode").val();
	var duration = $("#durationCode").val();
	var curPage = $("#curPage").val();
	$.ajax({
		url: mobilePath + "/project/list",
		data: {category: category, duration: duration, page: curPage}
	}).done(function(data){
		if(data.success) {
			var pjHtml = "";
			var len = data.value.results.length;
			if(len > 0) {
				// set total
				$("#filter .total i").html(data.value.total);
				for(var i = 0; i < len; i++) {
					var project = data.value.results[i];
					var html = "<li><a href='"+path+"/p/"+project.projectIdStr+"'>";
					html += "<div class='box'>";
					html += "<p class='status'><i>"+project.status+ "</i></p>";
					html += "<p class='title'>"+project.title+ "</p>";
					html += "<p class='price'>￥"+project.budget+ "</p>";
					html += "<p class='duration'>"+project.duration+ "</p>";
					html += "<p class='category'>"+project.category.name+" / "+project.subCategory.name+"</p>";
					html += "<p class='time'>"+project.createdTime+"</p>";
					html += "<dl><dd>已阅读<br/><i>"+project.reads+"</i>人</dd><dd>已申请<br/><i>"+project.bids+"</i>人</dd><dd>已推荐<br/><i>120</i>人</dd></dl>";
					html += "</div></a></li>";
					pjHtml += html;
				}
				// set page
				$(".pj-ls #pageDiv").html("<p class='page'>"+pageHtml(data.value.total, 12, curPage, "pagination")+"</p>");
			}
			else {
				$("#filter .total i").html(0);
				pjHtml += "<p class='empty'>暂无该类项目</p>"
			}
			$(".pj-ls ul").html(pjHtml);
		}
		else {
			UZDialogFade(data.message);
		}
	});
}
function pagination(page){
	$("#curPage").val(page);
	$('body').animate({scrollTop: "420px"},500);
	loadProject();
}

function getCategory() {
	$.ajax({
		url: mobilePath + "/category/list?level=1",
		method: "GET"
	}).done(function(data){
		if(data.success){
			category = data.value;
			// the page could be loaded with category
			var categoryCode = $("#categoryCode").val();
			if(categoryCode.length > 0) {
				setSubCategory(categoryCode);
			}
		}
		else {
			UZDialogFade("获取数据失败");
		}
	});
}
function setSubCategory(selCategory){
	var subCategory = category.software;
	if(selCategory == 'design') {
		subCategory = category.design;
	}
	else if(selCategory == 'marketing') {
		subCategory = category.marketing;
	}
	else if(selCategory == 'writing') {
		subCategory = category.writing;
	}
	var subCategoryList = "";
	var length = subCategory.length;
	for(var i=0; i < length; i++){
		subCategoryList += "<li>" + subCategory[i].name;
		if(i + 1 < length) {
			subCategoryList += "<i>\\</i>";
		}
		subCategoryList += "</li>";
	}
	
	$(".banner .sub ul").html(subCategoryList);
}