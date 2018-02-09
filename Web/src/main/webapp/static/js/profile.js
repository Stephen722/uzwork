// Get the city name
var newCityName;
$(function(){
	userImgInit();
	
	$("#info #detail .info-edit").click(function(){
		if(isValidEdit()) {
			$("#info #detail #info-edit").hide();
			$("#info #detail #info-edit-div").show();
			// Initialize the width of category input
			var spanBlen = $("#occup .select span b").length;
			if(spanBlen > 0) {
				var spanBWidth = 0;
				$("#occup .select span b").each(function(i){
					spanBWidth += $(this).outerWidth(true);
				});
				if(spanBWidth + 66 > 306) {
					$("#occup .select").outerWidth(spanBWidth + 66);
					$("#occup .select span").outerWidth(spanBWidth + 16);
				}
			}
		}
	});
	$("#info-post").click(function(){
		submitInfo();
	});
	$("#ct-post").click(function(){
		submitBrief();
	});
	$("#skill-post").click(function(){
		submitSkill();
	});
	
	$("#review-post").click(function(){
		if(isValidEdit()) {
			if($("#flow_img img").attr("src").indexOf("_color.png") == -1){
				UZDialogInfo("请先上传头像，再提交审核", "提交审核 - 提示信息");
				return false;
			}
			else if($("#flow_info img").attr("src").indexOf("_color.png") == -1){
				UZDialogInfo("请先填写基本信息，再提交审核", "提交审核 - 提示信息");
				return false;
			} 
			else if($("#flow_me img").attr("src").indexOf("_color.png") == -1){
				UZDialogInfo("请先填写自我介绍，再提交审核", "提交审核 - 提示信息");
				return false;
			}
			else if($("#flow_skill img").attr("src").indexOf("_color.png") == -1){
				UZDialogInfo("请先添加个人职业技能，再提交审核", "提交审核 - 提示信息");
				return false;
			}
			else if($("#flow_work img").attr("src").indexOf("_color.png") == -1){
				UZDialogInfo("请先添加作品案例，再提交审核", "提交审核 - 提示信息");
				return false;
			}
			
			UZDialogConfirm("审核期间您不能修改任何信息，确定提交审核？", "提交审核 - 提示信息", submitReview);
		}
	});

	// Set flow icon
	if($("#user_img img").attr("src").indexOf("user_icon.png") < 0) {
		setActiveFlow("flow_img");
	}
	if($("#title").val() != "") {
		setActiveFlow("flow_info");
	}
	if($("#brief").val() != "") {
		setActiveFlow("flow_me");
	}
	if($("#skill-edit i").length > 0) {
		setActiveFlow("flow_skill");
	}
	if($("#works #list .item").length > 0) {
		setActiveFlow("flow_work");
	}
	
	$("#info #detail .ct-edit").click(function(){
		if(isValidEdit()) {
			$("#info #detail #ct-edit").hide();
			$("#info #detail #ct-edit-div").show();
			$("#brief").val($("#info #detail #ct-edit").html().replace(/<br>/g, "\n"));
		}
	});
	$("#info #detail .skill-edit").click(function(){
		if(isValidEdit()) {
			$("#info #detail #skill-edit").hide();
			$("#info #detail #skill-edit-div").show();
			$("#info #detail #skill-edit-div .selSkill i img").click(function(){
				skillRemove($(this));
			});
		}
	});
	
	$("#info-edit-div #info-cancel").click(function(){
		$("#info #detail #info-edit-div").hide();
		$("#info #detail #info-edit").show();
	});
	
	$("#ct-edit-div #ct-cancel").click(function(){
		$("#info #detail #ct-edit-div").hide();
		$("#info #detail #ct-edit").show();
	});
	
	$("#skill-edit-div #skill-cancel").click(function(){
		$("#info #detail #skill-edit-div").hide();
		$("#info #detail #skill-edit").show();
	});
	
	$("#username").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("请输入您的昵称");
		}
		else if(val.length < 2 || val.length > 16){
			t.errorShow("请输入2-16位字符");
		}
	});
	$("#brief").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("请输入您的基本信息");
		}
		else if(val.length < 20 || val.length > 800){
			t.errorShow("请输入20-800的自我介绍");
		}
	});
	// Work years
	$("#years .select").click(function(){
		$("#years ul").toggle();
		$("#years .select").css("border-color", "#117bd8");
		$("#years ul li").click(function(){
			$("#years #workYears").val($(this).attr("id"));
			$("#years #selectedYears").text($(this).text());
			$("#years ul").hide();
			$("#years .select").css("border-color", "#ccc");
			$("#years ul").siblings(".error").text("");
		});
		$("#years ul").hover(function(){
			}, function(){
				$(this).hide();
				$("#years .select").css("border-color", "#ccc");
		});
	});
	
	// Set the initial city name
	newCityName = $("#city").val()
	// City selection
	$("#cities ul").hover(function(){
		$(this).show().css("border","1px solid #117bd8");
	}, function(){
		verifyCityId();
		// Hide ul if the city name is not changed
		if(newCityName == $("#city").val()) {
			$(this).hide();
		}
		
	});
	// Show the matched cities when input or click search box
	$("#city").on("input", function(){
		$("#cities #cityId").val(""); // reset city id
		citySearch();
		$("#cities ul").show().css("border","1px solid #117bd8");
	}).click(function() {
		citySearch();
	});
	
	// Show the matched skills when input
	$("#skill").on("input", function(){
		if($(this).val() == '') {
			$("#skill-edit-div .input-uz b").hide();
		}
		else {
			$("#skill-edit-div .input-uz b").show();
		}
		$("#skill-edit-div .error").text("");
		skillSearch();
		$("#skill-edit-div ul").show().css("border","1px solid #117bd8");
	});
	$("#skill-edit-div .input-uz b").click(function(){
		skillAdd();
	});
	$("#skill-edit-div ul").hover(function(){
	}, function(){
		$(this).hide();
		$("#skill").css("border-color", "#ccc");
	});
	// Occupation selection
	$("#occup .select").click(function(){
		$("#occup .list").toggle();
		$("#occup .select").css("border-color", "#117bd8");
		removeError();
	});
	$("#occup .list dl dd").click(function(){
		categoryAdd($(this));
		if($("#occup .error").text() == "") {
			$("#occup .select").css("border-color", "#ccc");
		}
	});
	$("#occup .list").hover(function(){
		}, function(){
			$(this).hide();
			if($("#occup .error").text() == "") {
				$("#occup .select").css("border-color", "#ccc");
			}
	});
	$("#occup .select span b img").click(function(event){
		categoryRemove($(this));
	});
	
	worksInit();
	worksImgInit();
	previewSrcHTML = $("#works-edit-div .upload").html();
	$("#works #works-add").click(function(){
		if(isValidEdit()) {
			// get the elements of works-edit-div, remove it from list and add it before list
			if($("#list").children("#works-edit-div").length > 0) {
				var worksId = $("#works-edit-div #worksId").val();
				if(worksId > 0) {
					// show previous works
					$("#list #"+worksId).show();
				}
				resetWorksEditDiv();
				var worksEditDiv = $("#works-edit-div").prop("outerHTML");
				$("#works-edit-div").remove();
				$("#list").before(worksEditDiv);
				// bind events again
				worksInit(); 
				worksImgInit();
			}
			$("#works-edit-div").show();
			$('body').animate({scrollTop: $("#works-edit-div").offset().top - 30},500);
		}
	});

});
// Search city
function citySearch() {
	var val = trimStr($("#city").val());
	if(val != "" && val.length > 0) {
		$.ajax({
			url: path+"/b/city",
			data: {
				city: val
			},
			dataType: "JSON",
			type: "POST",
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				if(data.success) {
					var cities = data.value;
					var cityList = "";
					var length = cities.length;
					if(length > 0) {
						for(var i=0; i < length; i++){
							cityList += "<li id='"+cities[i].cityId+"' class='"+cities[i].city+"'>" + cities[i].province + " - "+cities[i].city+ "</li>";
						}
						$("#cities ul").html(cityList);
						$("#cities ul").show();
						$("#cities ul li").click(function(){
							$("#cities ul").hide();
							$("#cities #city").val($(this).attr("class"));
							$("#cities #cityId").val($(this).attr("id"));
							$("#cities #city").focus().blur(function(){
								verifyCityId();
							});
							// Reset the new city name
							newCityName = $(this).attr("class");
						});
					}
					else {
						$("#cities ul").hide();
						$("#cities #city").focus().blur(function(){
							verifyCityId();
						});
					}
				}
				else {
					UZDialogFade(data.message);
				}
			}
		});
	}
}
// search skill
function skillSearch() {
	var val = trimStr($("#skill").val());
	if(val != "" && val.length > 0) {
		$.ajax({
			url: path+"/b/skill",
			data: {
				skill: val
			},
			dataType: "JSON",
			type: "POST",
			error: function(){UZDialogFade("请求失败");},
			success: function(data){
				if(data.success) {
					var skills = data.value;
					var skillList = "";
					var length = skills.length;
					if(length > 0) {
						for(var i=0; i < length; i++){
							skillList += "<li id='"+skills[i].id+"' class='"+skills[i].name+"'>" + skills[i].name + "</li>";
						}
						$("#skill-edit-div ul").html(skillList);
						$("#skill-edit-div ul").show();
						$("#skill-edit-div ul li").click(function(){
							skillSelect($(this));
						}).hover(function(){
							$(this).children("b").show();
						}, function(){
							$(this).children("b").hide();
						});
					}
					else {
						$("#skill-edit-div ul").hide();
					}
				}
				else {
					UZDialogFade(data.message);
				}
			}
		});
	}
}
//Verify the city
function verifyCityId() {
	if($("#cities ul").is(":hidden")) {
		var city = $("#cities #cityId").val();
		if(city == "") {
			$("#cities #city").errorShow("请选择你所在的城市");
		}
	}
}
// Adjust category width
function adjustCategoryWidth() {
	var spanBWidth = 0;
	$("#occup .select span b").each(function(){
		spanBWidth += $(this).outerWidth(true);
	});
	if(spanBWidth + 66 > 306) {
		if(spanBWidth + 66 <= 600) {
			$("#occup .select").outerWidth(spanBWidth + 66);
			$("#occup .select span").outerWidth(spanBWidth + 16);
			$("#occup .select").height(40);
		}
		else {
			$("#occup .select").height(80);
		}
	}
	else {
		$("#occup .select").outerWidth(306);
		$("#occup .select span").outerWidth(256);
	}
}
function categoryAdd(t) {
	var id = t.attr("id"), name = t.text();
	var duplicated = false, moreFive = false;;
	var categoryId = $("#category").val();
	var newCategoryId = "";
	if(categoryId == '') {
		newCategoryId = id;
	}
	else if(categoryId == id) {
		duplicated = true;
	}
	else if(categoryId.indexOf(",") > 0){
		var cats = categoryId.split(",");
		var len = cats.length;
		if(len < 5) {
			for(var i = 0; i < len; i++) {
				if(cats[i] == id) {
					duplicated = true;
					break;
				}
			}
			if(!duplicated) {
				newCategoryId = categoryId + "," + id;
			}
		}
		else {
			moreFive = true;
		}
	}
	else {
		newCategoryId = categoryId + "," + id;
	}
	
	if(moreFive) {
		$("#occup .list").hide();
		$("#occup .select").css("border-color","red");
		$("#occup .error").text("最多只能选择五个分类");
		setTimeout(function(){
			$("#occup .select").css("border-color","#ccc");
			$("#occup .error").text("");	
		}, 3000);
	}
	else if(duplicated) {
		$("#occup .list").hide();
		$("#occup .select span").children("#"+id).css("border-color","red");
		$("#occup .error").text("您已经选择了该分类");
	}
	else {
		// remove -请选择-
		if($("#category").val() == "") {
			$("#occup .select span").html("");
		}
		$("#category").val(newCategoryId);
		var inHtml = "<b id='"+id+"' class='"+name+"'>"+name+"<img src='"+staticPath+"/img/close.png' /></b>";
		$("#occup .select span").append(inHtml);
		// add event
		$("#occup .select span").children("#"+id).children("img").click(function(event){
			categoryRemove($(this));
		});
		adjustCategoryWidth();
		event.stopPropagation();
	}
}
function categoryRemove(t) {
	$("#occup .select").css("border-color", "#ccc");
	removeError();
	var id = t.parent().attr("id");
	t.parent().remove();
	var newCategoryId = "";
	var categoryId = $("#category").val();
	if(categoryId != '') {
		if(categoryId.indexOf(",") > 0){
			var cats = categoryId.split(",");
			for(i = 0; i < cats.length; i++) {
				if(cats[i] != id) {
					newCategoryId += cats[i] + ",";
				}
			}
			if(newCategoryId.substring(newCategoryId.length - 1) == ",") {
				newCategoryId = newCategoryId.substring(0, newCategoryId.length-1);
			}
		}
	}
	$("#category").val(newCategoryId);
	if(newCategoryId == "") {
		$("#occup .select").css("border-color","red");
		$("#occup .error").text("请选择你的职业类型");
	}
	adjustCategoryWidth();
	event.stopPropagation();
}
function removeError(){
	$("#occup .select span b").css("border-color","#ccc");
	$("#occup .error").text("");
}
function skillSelect(t){
	$("#skill-edit-div #skill").val(t.attr("class"));
	$("#skill-edit-div .input-uz b").show();
}
function skillAdd(){
	var name = $("#skill-edit-div #skill").val();
	if(name.length < 2|| name.length > 16) {
		$("#skill-edit-div .error").text("请输入2-16位的技能名称");
		return false;
	}
	
	var iLen = $("#skill-edit-div .selSkill i").length;	
	if(iLen == 10){
		$("#skill-edit-div .error").text("你最多只能添加10项技能");
	}
	else {
		var existing = false;
		$("#skill-edit-div .selSkill i").each(function(){
			if(name == $(this).text()) {
				existing = true;
				return;
			}
		});
		if(existing) {
			$("#skill-edit-div .error").text("你已经添加过该技能");
		}
		else {
			var inHtml = "<i class='sk"+iLen+"'>"+name+"<img src='"+staticPath+"/img/close.png' /></i>";
			$("#skill-edit-div .selSkill").append(inHtml);
			// add event
			$("#skill-edit-div .selSkill i img").click(function(event){
				skillRemove($(this));
			});
		}
	}
}
function skillRemove(t) {
	$("#skill-edit-div .error").text("");
	t.parent().remove();
	event.stopPropagation();
}
function submitInfo() {
	var username = trimStr($("#username").val());
	if(username == "") {
		$("#username").errorShow("请输入您的昵称");
		return false;
	}
	else if(username.length < 2 || username.length > 16){
		$("#username").errorShow("请输入2-16位字符");
		return false;
	}
	var workYears = $("#workYears").val();
	if(workYears == "") {
		$("#years .error").text("请选择您的工作年限");
		return false;
	}
	var title = trimStr($("#title").val());
	if(title == "") {
		$("#title").errorShow("请输入您的职位名称");
		return false;
	}
	else if(title.length < 2 || title.length > 20) {
		$("#title").errorShow("请输入2-20位字符");
		return false;
	}
	
	var cityId = $("#cityId").val();
	if(cityId == "") {
		$("#city").errorShow("请选择您所在的城市");
		return false;
	}
	var category = $("#category").val();
	if(category == "") {
		$("#occup .error").text("请选择您的职业类型");
		return false;
	}
	
	$("#info-post").val("保存中...");
	$("#info-post").attr("disabled", true);
	$("#info-cancel").attr("disabled", true);
	$.ajax({
		url: path+"/uc/profile",
		data: {
			process: "info",
			username: username,
			workYears: workYears,
			city: $("#city").val(),
			title: title,
			category: category
		},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				UZDialogFade("成功保存信息");
				$("#info #detail #info-edit-div").hide(300);
				$("#info #detail #info-edit").show(300);
				// Update page info
				$("#info-edit .me .mname").text(username);
				$("#info-edit .me .mtitle").text(title);
				$("#info-edit .adv .mcity").text($("#city").val());
				$("#info-edit .adv .mcat").remove();
				var selectedCats = "";
				$("#occup .select span b").each(function(){
					var catName = $(this).attr("class");
					selectedCats += "<i class='mcat'>"+catName+"</i>";
				});
				$("#info-edit .adv").append(selectedCats);
				
				setActiveFlow("flow_info");
			}
			else {
				$("#occup .error").text(data.message);
			}
		},
		beforeSend: function () {
			loading(true);
	    },
		complete:function(){
			loading(false);
			$("#info-post").val("保存");
			$("#info-post").attr("disabled",false);
			$("#info-cancel").attr("disabled",false);
		}
	});
}
function submitBrief() {
	$("#ct-post").val("保存中...");
	$("#ct-post").attr("disabled", true);
	$("#ct-cancel").attr("disabled", true);
	var t = $("#brief");
	var val = trimStr(t.val());
	if(val == "") {
		t.errorShow("请输入您的基本信息");
	}
	else if(val.length < 20 || val.length > 800){
		t.errorShow("请输入20-800的自我介绍");
	}
	$.ajax({
		url: path+"/uc/profile",
		data: {
			process: "brief",
			brief: val
		},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				UZDialogFade("成功保存信息");
				$("#info #detail #ct-edit-div").hide(300);
				$("#info #detail #ct-edit").show(300);
				// Update page info
				$("#ct-edit").html(t.val().replace(/\r\n|\n/g, "<br>"));
				setActiveFlow("flow_me");
			}
			else {
				$("#occup .error").text(data.message);
			}
		},
		beforeSend: function () {
			loading(true);
	    },
		complete:function(){
			loading(false);
			$("#ct-post").val("保存");
			$("#ct-post").attr("disabled",false);
			$("#ct-cancel").attr("disabled",false);
		}
	});
}
function submitSkill() {
	var selSkill = "";
	var sLen = $("#skill-edit-div .selSkill i").length;
	if(sLen > 0) {
		$("#skill-edit-div .selSkill i").each(function(i){
			var skill = $(this).text();
			if(skill != undefined) {
				selSkill += skill;
				if(i + 1 < sLen) {
					selSkill += ",";
				}
			}
		});
	}
	else {
		$("#skill-edit-div .error").text("请选择你的职业技能");
		return false;
	}
	
	$("#skill-post").val("保存中...");
	$("#skill-post").attr("disabled", true);
	$("#skill-cancel").attr("disabled", true);
	$.ajax({
		url: path+"/uc/profile",
		data: {
			process: "skill",
			skill: selSkill
		},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				UZDialogFade("成功保存信息");
				$("#info #detail #skill-edit-div").hide(300);
				$("#info #detail #skill-edit").show(300);
				// Update page info
				$("#skill-edit").html($("#skill-edit-div .selSkill").html());
				$("#skill-edit i img").remove();
				setActiveFlow("flow_skill");
			}
			else {
				$("#skill-edit-div .error").text(data.message);
			}
		},
		beforeSend: function () {
			loading(true);
	    },
		complete:function(){
			loading(false);
			$("#skill-post").val("保存");
			$("#skill-post").attr("disabled",false);
			$("#skill-cancel").attr("disabled",false);
		}
	});
}

function setActiveFlow(id) {
	var img_src = $("#"+id+" img").attr("src");
	$("#"+id).css("color", "#fff");
	if(img_src.indexOf("_color.png") == -1) {
		$("#"+id+" img").attr("src", img_src.replace(".png", "_color.png"));
	}
}
function setInactiveFlow(id) {
	var img_src = $("#"+id+" img").attr("src");
	if(img_src.indexOf("_color.png") > -1) {
		$("#"+id).css("color", "#888");
		$("#"+id+" img").attr("src", img_src.replace("_color.png", ".png"));
	}
}

function submitReview() {
	// hide the edit div
	$("#info-edit-div #info-cancel").click();
	$("#ct-edit-div #ct-cancel").click();
	$("#skill-edit-div #skill-cancel").click();
	worksCancel();
	
	$.ajax({
		url: path+"/uc/profile",
		data: {process: "review"},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				UZDialogInfo("您已提交审核，请耐心等待", "提交审核 - 提示信息");
				$("#flow span").html("您已提交审核，请耐心等待");
				$("#flow span").attr("id", "CHECKING");
			}
			else {
				UZDialogInfo(data.message, "提交审核 - 提示信息");
			}
		},
		beforeSend: function () {
			$("#review-post").val("提交中...");
			$("#review-post").attr("disabled", true);
			loading(true);
	    },
		complete:function(){
			$("#review-post").val("提交审核");
			$("#review-post").attr("disabled",false);
			loading(false);
		}
	});
}
var file = null;
var imgData = "";
var imgRatio = 0;
function userImgInit() {
	$("#user_img").click(function(){
		if(isValidEdit()) {
			$("#user_img_upload").click();
		}
	});

	$("#user_img_upload").change(function(){
		imgRatio = 0;
		var files = this.files;
	    if (files && files.length > 0) {
	        file = files[0];
	        
        	if (!/^image\/\w+/.test(file.type)) {
	        	UZDialogFade('请选择一张gif、jpg或png格式的图片!');
	        	return false;
	        }
	        
	        if(file.size > 1024 * 1024 * 2) {
	            UZDialogFade('图片大小不能超过 2MB!');
	            return false;
	        }
	        var URL = window.URL || window.webkitURL;
	        var imgURL = URL.createObjectURL(file);
	       
	        $("#user_img_upload").val("");
	        $("#user_img_select").UZDialog();
	        
	        var $image = $("#user_img_select #crop_img"), $image_icon = $("#user_img_select .icon");
	        $image.cropper('destroy').attr("src",imgURL).cropper({
        		aspectRatio: 1,
            	guides: false,
            	center: false,
                highlight: false,
                background: false,
                toggleDragModeOnDblclick: false,
                autoCropArea: 0.5,
                viewMode: 1,
                dragMode: 'move',
                cropBoxMovable: false,
                cropBoxResizable: false,
                minCropBoxWidth: 250,
                zoom: function(e) {
                	if(e.ratio > 3.0) {
                		return false;
                	}
                	else {
                		imgRatio = e.ratio;
                	}
                },
                crop: function(e) {
					imgData = [
					'{"x":' + e.x,
					'"y":' + e.y,
					'"height":' + e.height,
					'"width":' + e.width,
					'"rotate":' + e.rotate + '}'
					].join();
                }
            });
	        
	        var submitEvent = $._data($("#user_img_select .submit")[0], "events");
	        if(submitEvent == undefined || submitEvent["click"] == undefined){
	        	$("#user_img_select .submit").click(function(){
	        		submitUserImg();
	        	});
	        }
	        
	        var outEvent = $._data($image_icon.children(".out")[0], "events");
	        if(outEvent == undefined || outEvent["click"] == undefined){
	        	$image_icon.children(".out").click(function(){
		        	$image.cropper("zoom", 0.1);
		    	});
	        }
        
	        var inEvent = $._data($image_icon.children(".in")[0], "events");
	        if(inEvent == undefined || inEvent["click"] == undefined){
		        $image_icon.children(".in").click(function(){
		    		$image.cropper("zoom", -0.1);
		    	});
	        }
	        
	        var scaleEvent = $._data($image_icon.children(".scale")[0], "events");
	        if(scaleEvent == undefined || inEvent["click"] == undefined){
		        $image_icon.children(".scale").click(function(){
		    		$image.cropper("rotate", 45);
		    	});
	        }
	    }
	});
}

function submitUserImg(){
	var formData = new FormData($("#userImgForm")[0]);
	formData.append("userImg", file);
	formData.append("userImgName", file.name);
	formData.append("userImgData", imgData);
	formData.append("userImgRatio", imgRatio);
	
	$("#user_img_select .submit").text("上传中...");
	$("#user_img_select .submit").attr("disabled", true);
	
	$.ajax({
		url: path+"/uc/profile?process=userImg",
		data: formData,
		dataType: "JSON",
		type: "POST",
		processData: false,
        contentType: false,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				setActiveFlow("flow_img");
				UZDialogFade("上传头像成功！");
				UZDialogClose();
				$("#user_img img").attr("src", imagePath+"/user/"+data.value);
			}
			else {
				UZDialogInfo("上传头像失败，请联系客服", "上传头像 - 提示信息");
			}
		},
		beforeSend: function () {
			loading(true);
	    },
		complete: function(){
			loading(false);
			$("#user_img_select .submit").text("上传");
			$("#user_img_select .submit").attr("disabled", false);
			$("#review-post").attr("disabled",false);
			$("#user_img_select").hide();
		}
	});
}
var worksFile = null;
var worksImgData = "";
var worksImgRatio = 0;
var previewSrcHTML = "";
var previousWorksImgHTML = "";
var worksImgChanged = false;
function worksImgInit() {
	$("#works-edit-div .upload").click(function(){
		$("#works_img_upload").click();
	});

	$("#works_img_upload").change(function(){
		var files = this.files;
	    if (files && files.length > 0) {
	    	worksFile = files[0];
	        
        	if (!/^image\/\w+/.test(worksFile.type)) {
	        	UZDialogFade('请选择一张gif、jpg或png格式的图片!');
	        	return false;
	        }
	        
	        if(worksFile.size > 1024 * 1024 * 3) {
	            UZDialogFade('图片大小不能超过 3MB!');
	            return false;
	        }
	        var URL = window.URL || window.webkitURL;
	        var worksImgURL = URL.createObjectURL(worksFile);
	        
	        worksImgChanged = false;
	        previousWorksImgHTML = $("#works-edit-div .upload").html();
	        
	        $("#works_img_upload").val("");
	        $("#works_img_select").UZDialog();
	        
	        var $worksImage = $("#works_img_select #wks_img"), $worksImage_icon = $("#works_img_select .icon");
	        $worksImage.cropper('destroy').attr("src", worksImgURL).cropper({
        		aspectRatio: 16/7,
            	guides: false,
            	center: false,
                highlight: false,
                background: false,
                toggleDragModeOnDblclick: false,
                autoCropArea: 0.8,
                viewMode: 1,
                dragMode: 'move',
                zoomable: false,
                cropBoxMovable: false,
                cropBoxResizable: false,
                preview: "#works-edit-div .upload",
                minCropBoxWidth: 900,
                zoom: function(e) {
                	if(e.ratio > 3.0) {
                		return false;
                	}
                	else {
                		worksImgRatio = e.ratio;
                	}
                },
                crop: function(e) {
                	worksImgData = [
					'{"x":' + e.x,
					'"y":' + e.y,
					'"height":' + e.height,
					'"width":' + e.width,
					'"rotate":' + e.rotate + '}'
					].join();
                }
            });
	        
	        var submitEvent = $._data($("#works_img_select .submit")[0], "events");
	        if(submitEvent == undefined || submitEvent["click"] == undefined){
	        	$("#works_img_select .submit").click(function(){
	        		worksImgChanged = true;
	        		UZDialogClose();
	        	});
	        }
	    }
	});
}
function worksInit(){
	$("#works #works-post").click(function(){
		return submitWorks();
	});
	
	$("#works #works-cancel").click(function(){
		worksCancel();
	});
	
	$("#works-edit-div #worksName").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("请输入作品名称");
		}
		else if(val.length < 2 || val.length > 20){
			t.errorShow("请输入2-20字的作品名称");
		}
	});
	
	$("#works-edit-div #worksResponsibility").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val == "") {
			t.errorShow("请输入您的职责");
		}
		else if(val.length < 2 || val.length > 20){
			t.errorShow("请输入2-20字的职责");
		}
	});
	
	$("#works-edit-div #worksLink").blur(function(){
		var t = $(this);
		var val = t.val();
		if((val.length > 0 && val.length < 10) || val.length > 200){
			t.errorShow("请输入10-200字的作品链接");
		}
	});
	
	$("#works-edit-div #worksDescription").blur(function(){
		var t = $(this);
		var val = t.val();
		if(val.length < 10 || val.length > 500){
			t.errorShow("请输入10-500字的作品描述");
		}
	});
	
	$("#works #list .edit").click(function(){
		if(isValidEdit()) {
			worksEdit(this);
		}
	});
	
	$("#works #list .del").click(function(){
		if(isValidEdit()) {
			var worksId = $(this).parents(".item").attr("id");
			var title = $(this).parent().siblings(".p").children("b").text();
			UZDialogConfirm("确定删除该作品？<br/>"+title,"提交审核 - 提示信息", worksDelete, worksId);
		}
	});
}
function worksEdit(t){
	worksFile = null;
	worksImgChanged = false;
	
	var _p = $(t).parent().parent().children(".p");
	var worksId = $(t).parents(".item").attr("id");
	var link = $(t).siblings("a").attr("href");
	var title = _p.children("b").text();
	var responsibility = _p.children("i").text();
	var description = _p.children("span").text();
	// re-display the previous work
	if($("#works-edit-div").attr("class") != undefined) {
		var preWorksId = $("#works-edit-div").attr("class");
		$("#works-edit-div").removeClass(preWorksId);
		$("#list #"+preWorksId).show();
	}
	// get the elements of works-edit-div, and remove previous works-edit-div
	var worksEditDiv = $("#works-edit-div").prop("outerHTML");
	$("#works-edit-div").remove();
	$("#list #"+worksId).after(worksEditDiv);
	$("#list #"+worksId).hide();
	// because append works edit dev, system binds events again
	worksInit();
	
	// show the image
	if($(t).parents(".item").children(".img").children("img").length > 0) {
		var imageHTML = $(t).parents(".item").children(".img").html();
		$("#list #works-edit-div .upload").html(imageHTML);
	}
	worksImgInit();
	$("#list #works-edit-div #worksName").val(title);
	$("#list #works-edit-div #worksId").val(worksId);
	$("#list #works-edit-div #worksResponsibility").val(responsibility);
	$("#list #works-edit-div #worksLink").val(link);
	$("#list #works-edit-div #worksDescription").val(description);
	$("#list #works-edit-div").addClass(worksId).show();
	
	$('body').animate({scrollTop: $("#list #works-edit-div").offset().top - 30},500);
}
function worksCancel() {
	var worksId = $("#works-edit-div #worksId").val();
	resetWorksEditDiv();
	$("#works-edit-div").hide();
	if(worksId > 0) {
		// show previous works
		$("#list #"+worksId).show();
	}
}
function worksDelete(id){
	$.ajax({
		url: path+"/uc/profile",
		data: {
			process: "worksDelete",
			id: id
		},
		dataType: "JSON",
		type: "POST",
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				UZDialogClose();
				$("#list #"+id).delay(1000).hide(200);
				$("#list #"+id).remove();
				UZDialogFade("作品已被删除");
				// disable icon if there is no any works
				if($("#list .item").length <= 0) {
					setInactiveFlow("flow_work");
					$('body').animate({scrollTop: $("#works").offset().top - 30},500);
				}
			}
			else {
				UZDialogFade("作品删除失败，请联系客服");
			}
		},
		beforeSend: function () {
			loading(true);
	    },
		complete:function(){
			loading(false);
		}
	});
}
function worksImgCancel(){
	worksImgChanged = false;
	$("#works-edit-div .upload img").remove();
	if(previousWorksImgHTML.length > 0) {
		$("#works-edit-div .upload").html(previousWorksImgHTML);
	}
	else {
		$("#works-edit-div .upload").html(previewSrcHTML);	
	}
	UZDialogClose();
}
function resetWorksEditDiv() {
	// reset
	$("#worksId").val(0);
	$("#worksName").val("");
	$("#worksLink").val("");
	$("#worksResponsibility").val("");
	$("#worksDescription").val("");
	$("#works-edit-div .upload").html(previewSrcHTML); //reset image
	// clear the wks_img parent html 
	$("#works-edit-div #works_img_upload").val("");
	$("#works_img_select #wks_img").parent().html("<img id='wks_img' src='' />");
	// clear error
	$("#works-edit-div .error-msg").each(function(){
		$(this).text("").hide();
		var cls=$(this).attr("class");
		if($(this).siblings(".input-uz")) {
			$(this).siblings(".input-uz").removeClass("error-bd");
		}
	});
}
function submitWorks() {
	var worksId = $("#worksId").val();
	var worksName = $("#worksName").val();
	if(worksName == "") {
		$("#worksName").errorShow("请输入作品名称");
		return false;
	}
	else if(worksName.length < 2 || worksName.length > 20){
		$("#worksName").errorShow("请输入2-20字的作品名称");
		return false;
	}
	
	var worksResponsibility = $("#worksResponsibility").val();
	if(worksResponsibility == "") {
		$("#worksResponsibility").errorShow("请输入您的职责");
		return false;
	}
	else if(worksResponsibility.length < 2 || worksResponsibility.length > 20){
		$("#worksResponsibility").errorShow("请输入2-20字的职责");
		return false;
	}

	var worksLink = $("#worksLink").val();
	if((worksLink.length > 0 && worksLink.length < 10) || worksLink.length > 200){
		$("#worksLink").errorShow("请输入10-200字的作品链接");
		return false;
	}

	var worksDescription = $("#worksDescription").val();
	if(worksDescription.length < 10 || worksDescription.length > 500){
		$("#worksDescription").errorShow("请输入10-500字的作品描述");
		return false;
	}
	
	var formData = new FormData($("#worksForm")[0]);
	if(worksImgChanged && worksFile){
		formData.append("worksImg", worksFile);
		formData.append("worksImgName", worksFile.name);
		formData.append("worksImgData", worksImgData);
		formData.append("worksImgRatio", worksImgRatio);
	}
	
//	formData.append("process", works);
	formData.append("id", worksId);
	formData.append("title", worksName);
	formData.append("responsibility", worksResponsibility);
	formData.append("link", worksLink);
	formData.append("description", worksDescription);

	$.ajax({
		url: path+"/uc/profile?process=works",
		data: formData,
		dataType: "JSON",
		type: "POST",
		processData: false,
	    contentType: false,
		error: function(){UZDialogFade("请求失败");},
		success: function(data){
			if(data.success){
				var curWorksId = worksId;
				var newWorkId = data.value.worksId;
				var worksImg = data.value.worksImg;
				$("#works-edit-div").hide();
				UZDialogFade("成功保存信息");
				if(worksId > 0) { // edit
					var idString = "#list #" + worksId;
					$(idString).show();
					$(idString+" .text .p b").text(worksName);
					$(idString+" .text .p i").text(worksResponsibility);
					$(idString+" .text .p span").text(worksDescription);
					if(worksImg.length > 0) {
						// if this works has image, then udpate it, otherwise append the img element
						if($(idString+" .img").children("img").length > 0) {
							$(idString+" .img img").attr("src", imagePath+"/works/"+worksImg);
						}
						else {
							$(idString+" .img").html("<img src='" + imagePath+"/works/" + worksImg+"' />");
						}
					}
					if(worksLink.length > 0) {
						if($(idString+" .text a").length > 0) {
							$(idString+" .text a").attr("href",worksLink);
						}
						else {
							$(idString+ " .text .more").append("<a href='"+worksLink+"' target='_blank'>作品链接</a>");
						}
					}
				}
				else { // new
					// append the new work to list
					curWorksId = newWorkId;
					var inHtml = "<div class='item' id='"+newWorkId+"'>";
					if(worksImg.length > 0) {
						inHtml += "<p class='img'><img src='" + imagePath + "/works/" + worksImg+"'></p>";
					}
					else {
						inHtml += "<p class='img'></p>";
					}
					inHtml += "<div class='text'><p class='p'><b>"+worksName+"</b><i>"+worksResponsibility+"</i><span>"+worksDescription+"</span></p>";
					inHtml += "<p class='more'><b class='edit'>编辑</b><b class='del'>删除</b><br/>";
					if(worksLink.length > 0) {
						inHtml += "<a href='"+worksLink+"' target='_blank'>作品链接</a>";
					}
					inHtml += "</p></div></div>";
					$("#works #list").prepend(inHtml);
					
					// bind event
					$("#works #list .edit").click(function(){
						worksEdit(this);
					});
					
					$("#works #list .del").click(function(){
						if(isValidEdit()) {
							var worksId = $(this).parents(".item").attr("id");
							var title = $(this).parent().siblings(".p").children("b").text();
							UZDialogConfirm("确定删除该作品？<br/>"+title,"提交审核 - 提示信息", worksDelete, worksId);
						}
					});
				}
				// scroll to the works div
				$('body').animate({scrollTop: $("#list #" + curWorksId).offset().top - 30},500);
				
				// reset
				$("#worksId").val(0);
				$("#worksName").val("");
				$("#worksLink").val("");
				$("#worksResponsibility").val("");
				$("#worksDescription").val("");
				$("#works-edit-div .upload").html(previewSrcHTML); //reset image
				// clear the wks_img parent html 
				$("#works-edit-div #works_img_upload").val("");
				$("#works_img_select #wks_img").parent().html("<img id='wks_img' src='' />");
				setActiveFlow("flow_work");
			}
			else {
				$("#works-edit-div #worksDescription").errorShow(data.message);
			}
		},
		beforeSend: function () {
			$("#works-post").val("保存中...");
			$("#works-post").attr("disabled", true);
			$("#works-cancel").attr("disabled", true);
			$("#works_img_upload").attr("disabled",true);
			loading(true);
	    },
		complete:function(){
			$("#works-post").val("保存");
			$("#works-post").attr("disabled",false);
			$("#works-cancel").attr("disabled",false);
			$("#works_img_upload").attr("disabled",false);
			loading(false);
		}
	});
	return true;
}
function isValidEdit() {
	var status = $("#flow span").attr("id");
	if(status && "CHECKING" == status) {
		UZDialogInfo("审核期间不能修改任何信息，请耐心等待", "正在审核......");
		return false;
	}
	else {
		return true;
	}
}