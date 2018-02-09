$(function(){
	$(".json").html(function(){
		$(this).html($(this).html().replace(/\r\n|\n/g, "<br>"));
	});
	$(".link").click(function(){
		var cat = $(this).attr("cat");
		var page = $(this).attr("page");
		$("#main-iframe", window.parent.document).attr("src", cat + "/" + page + ".html");
		
		// sel
		$("#nav dd", window.parent.document).removeClass("sel");
		$("#nav #"+cat+" ."+page, window.parent.document).addClass("sel");
		
		var catDD = "#nav #"+cat + " dd";
		var catDTEM = "#nav #"+cat +" dt em";
 		if($(catDTEM, window.parent.document).hasClass("up") == false) {
			$(catDD, window.parent.document).each(function(){
				$(this).show(); // don't add any parameter to show()
			});
			$(catDTEM, window.parent.document).removeClass("down").addClass("up");
		}
		
	});
})