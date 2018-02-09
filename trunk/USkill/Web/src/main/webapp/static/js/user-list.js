var category;
$(function(){
	loadUser();
	getCategory();
	var categoryCode = $("#categoryCode").val();
	var categoryName = "悠哉人才";
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
		var catId = $(this).attr("id");
		var txt = $(this).text();
		$("#filter .list1 span b").text(txt);
		$(".banner .cat b").text(txt);
		$("#categoryCode").val(catId);
		$("#curPage").val(1);
		setSubCategory(catId);
		loadUser();
	});
	$("#filter .list2 dl dd").click(function() {
		$(this).children("span").css("color","#444");
		$(this).parent().fadeOut(200);
		$(this).siblings().removeClass("sel");
		$(this).addClass("sel");
		$("#filter .list2 span b").text($(this).text());
		$("#workYearsCode").val($(this).attr("id"));
		$("#curPage").val(1);
		loadUser();
	});
	$("#filter .list3 span").click(function() {
		$("#categoryCode").val("");
		$("#workYearsCode").val("");
		$("#curPage").val(1);
		$("#filter .list1 span b").text("人才类型");
		$("#filter .list1 dl dd").removeClass("sel");
		$("#filter .list2 span b").text("工作年限");
		$("#filter .list2 dl dd").removeClass("sel");
		$(".banner .cat b").text("悠哉人才");
		$(".banner .sub ul").html("<li>软件开发<i>\\</i></li><li>创意设计<i>\\</i></li><li>推广运营<i>\\</i></li><li>写作翻译</li>");
		loadUser();
	});
});
function loadUser() {
	var category = $("#categoryCode").val();
	var workYears = $("#workYearsCode").val();
	var curPage = $("#curPage").val();
	$.ajax({
		url: mobilePath + "/user/list",
		data: {category: category, workYears: workYears, page: curPage}
	}).done(function(data){
		if(data.success) {
			var urHtml = "";
			var len = data.value.results.length;
			if(len > 0) {
				// set total
				$("#filter .total i").html(data.value.total);
				for(var i = 0; i < len; i++) {
					var user = data.value.results[i];
					var html = "<li class='clearfix'><p class='photo'><a href='"+path+"/u/"+user.userIdStr+"'>";
					html += "<img src='"+imagePath+"/user/"+user.idImage+"' /></a>";
					html += "<em><img src='"+staticPath+"/img/star-red.png'/><img src='"+staticPath+"/img/star-red.png'/><img src='"+staticPath+"/img/star-red.png'/><img src='"+staticPath+"/img/star-gray.png'/></em>";
					html += "</p><div class='writing'>";
					
					var indLen = user.individualWorks.length;
					if(indLen <= 0) {
						html += "<p>暂无可展示个人作品</p>";
					}
					else {
						for(var j = 0; j < indLen; j++) {
							var work = user.individualWorks[j];
							html += "<p><b>"+work.title+"</b><span>";
							if(work.image) {
								html += "<img src='"+imagePath+"/works/"+work.image+"' />";
							}
							else {
								html += work.description;
							}
							html += "</span></p>";
						}
					}
					html += "</div><div class='info'><a href='"+path+"/u/"+user.userIdStr+"'>";
					html += "<p class='name'><span>"+user.username+"</span><i>|</i><em>"+user.title+"</em></p>";
					html += "</a><p class='tag'>";
					
					var catLen = user.category.length;
					for(var c = 0; c < catLen; c++) {
						var cat = user.category[c];
						html += "<i>"+ cat.name;
						if(c + 1 < catLen) {
							html += ",";
						}
						html += "</i>";
					}
					html += "</p><p class='adv htmlRN'>"+user.brief+"</p>";
					html += "</div></li>";
					urHtml += html;
				}
				// set page
				$(".ur-ls #pageDiv").html("<p class='page'>"+pageHtml(data.value.total, 6, curPage, "pagination")+"</p>");
			}
			else {
				$("#filter .total i").html(0);
				urHtml += "<p class='empty'>暂无该类人才</p>"
			}
			$(".ur-ls ul").html(urHtml);
		}
		else {
			UZDialogFade(data.message);
		}
	}).always(function(){
		// replace \r\n
		$(".htmlRN").each(function(){
			$(this).html($(this).html().replace(/\r\n|\n/g, "<br>"));
		});
		$(".ur-ls ul li").hover(function(){
				$(this).css("border-bottom", "1px solid #00A0E9");
				$(this).prev("li").css("border-bottom", "1px solid #fff");
			},
			function() {
				$(this).css("border-bottom", "1px solid #ccc");
				$(this).prev("li").css("border-bottom", "1px solid #ccc");
			}
		);
	});
}
function pagination(page){
	$("#curPage").val(page);
	$('body').animate({scrollTop: "420px"},500);
	loadUser();
}
function getCategory() {
	$.ajax({
		url: mobilePath + "/category/list?level=1",
		method: "GET",
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