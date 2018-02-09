var category;
var selCategory;
$(function(){
	if($("#logUserId").val() <= 0) {
		UZDialogBG("show");
		addCookie("backUrl", window.location.href);
		$("#login-dialog").show();
	}
	getCategory();
	$("#cat-list p").click(function(){
		$(this).addClass("selected").siblings().removeClass("selected");
		selCategory = $(this).find("span").attr("class");
		$("#category").val(selCategory);
		// Set sub category for the selected category
		setSubCategory();
		$("#subCategory").val("");
		$("#sub-cat #selectedSubCategory").text("- 请选择 -");
		$("#sub-cat #list").hide();
		$("#sub-cat .select").css("border-color", "#999");
		$("#sub-cat").siblings(".error").text("");
	}).hover(function(){
			$(this).addClass("selected");
		}, function(){
			if($(this).siblings().hasClass("selected")) {
				$(this).removeClass("selected");
			}
	});
	
	$("#sub-cat .select").click(function(){
		setSubCategory();
		$("#sub-cat #list").toggle();
		$("#sub-cat .select").css("border-color", "#117bd8");
		$("#sub-cat #list li").click(function(){
			$("#sub-cat #subCategory").val($(this).attr("id"));
			$("#sub-cat #selectedSubCategory").text($(this).text());
			$("#sub-cat #list").hide();
			$("#sub-cat .select").css("border-color", "#999");
			$("#sub-cat").siblings(".error").text("");
		});
		$("#sub-cat #list").hover(function(){
			}, function(){
				$(this).hide();
		});
	});
	
	$("#title").blur(function(){
		var len = trimStr($(this).val()).length;
		if((len > 0 && len < 5) || len > 50) {
			$(this).errorShow("请输入5-50个字的项目标题");
		}
	});
	$("#example").click(function(){
		$("#pj-desc .example").toggle().hover(function(){
		}, function(){
			$(this).hide();
		});
	})
	
	$("#description").blur(function(){
		var len = trimStr($(this).val()).length;
		if((len > 0 && len < 20) || len > 2000) {
			$(this).errorShow("请输入20-2000字的项目描述");
		}
	});
	
	$(document).on("click",".fileList span",function(){
		$("#select_file").val('');
		$(this).remove();
	});
	
	$("#pj-budget,#pj-duration").on("click","li",function(){
		$(this).addClass("selected").siblings().removeClass("selected");
		$(this).parent().siblings("input").val($(this).attr("id"));
	});
	
	$("#subimt input").click(function(){
		var action = $(this).attr("id");
		var valid = true;
		if("post" == action) {
			var subCategory = $("#subCategory").val();
			var title = trimStr($("#title").val());
			var description = trimStr($("#description").val());
			if(subCategory == "" || subCategory.length < 2) {
				$("#sub-cat").siblings(".error").text("请选择您的项目所属具体类型");
				$("#sub-cat .select").css("border-color", "#ff4200");
				valid = false;
			}
			if(title == "" || title.length < 5 || title.length > 50) {
				$("#title").errorShow("请输入5-50字的项目标题");
				valid = false;
			}
			if(description == "" || description.length < 20 || description.length > 2000) {
				$("#description").errorShow("请输入20-2000字的项目描述");
				valid = false;
			}
		}
		if(valid) {
			$("#projectForm").submit();
		}
		else {
			$('body').animate({scrollTop: "290px"},500);
		}
		
	});
});

function file(val){
	if(val!='' && val!='undefined' &&  val!=undefined){
		val=val.split("\\");
		$(".fileList").html("<span><em>"+val[val.length-1]+"</em><i></i></span>");
	}else{
		$(".fileList span").remove();	
	}
}

function setSubCategory(){
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
		subCategoryList += "<li id='"+subCategory[i].code+"'>" + subCategory[i].name + "</li>";
	}
	
	$("#sub-cat #list").html(subCategoryList);
}

function getCategory() {
	$.ajax({
		url: path+"/cat",
		data: {
			process: "category"
		},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				category = data.value;
			}
			else {
				UZDialogFade("获取数据失败");
			}
		}
	});
}