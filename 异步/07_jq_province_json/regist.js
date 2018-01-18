$(function(){
	// 为省份下拉列表绑定事件:
	$("#province").change(function(){
		var pid = $(this).val();
		$.post("/day15/ServletDemo8",{"pid":pid},function(data){
			// alert(data);
			// JS识别JSON:
			// var json = eval(data);
			var $city = $("#city");
			$city.html("<option>-请选择-</option>");
			$(data).each(function(i,n){
				// alert(n.cname);
				$city.append("<option value='"+n.cid+"'>"+n.cname+"</option>");
			});
		},"json");
	});
});