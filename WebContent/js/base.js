/**
 * New node file
 */
var basepath="http://localhost:8080/BookMarkSys";

$(function(){
	findBookMarkListByPage(1);
	$("#keywordInput").keyup(function(){
		findBookMarkListByPage(1);
	});
});


function findBookMarkListByPage(pageNum){
	var title = $("#keywordInput").val();
	$.ajax({
		url : basepath + "/bookMark/getBookMarks.do",
		type : "post",
		dataType : "json",
		data:{
			'title':title,
			'pageNum':pageNum,
			'pageSize':10,
		},
		success : function(result) {
			if (result.status == 0) {
				alert(result.msg);
			}
			if($("#keywordInput").val()!=""){
				$("#resultNum").html("搜到"+result.page.totalRowNum+"条记录");
			}
			
			appendBookmarks(result.data,title);
			initPage(result.page);
		}
	})
}

