/**
 * 
 */
function timeFormat(timeStamp) {
	var date = new Date(parseInt(timeStamp));
	return 'created@' + date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ (date.getDate() + 1);
}

function appendBookmarks(data, title) {
	var str = "";
	$(data).each(
			function(index, item) {
				var titles = "'" + item.title + "'";
				var redTitle = item.title.replace(title,
						'<span style="background-color:red">' + title
								+ '</span>');
				str += "<li class='bookmarkTitle'>" + redTitle + "</li>";
				str += "<li class='bookmarkCreated'>"
						+ timeFormat(item.created)
						+ "&nbsp;&nbsp;<input type='button' class='deleteButton' value='删除' title='"
						+ item.title
						+ "'  onclick='deleteBookMark(this);'/></li>";
			});
	$('ul').html(str);
}

/**
 * 删除bookMark
 * 
 * @param showP
 * @param page
 */
$(function() {
	$("#addBookMark").click(function() {
		var title = $("#title").val();
		if (title.trim() == "") {
			$("#msg").html("请输入书签");
			return;
		}
		$.ajax({
			url : basepath + "/bookMark/saveBookMark.do",
			type : "post",
			dataType : "json",
			data : {
				'title' : title,
			},
			success : function(result) {
				if (result.status == 0) {
					$("#msg").html(result.msg);
				}
				$("#myModal").css("visibility", "hidden");
				$(".reveal-modal-bg").css("display", "none");
				//alert(result.msg);
				findBookMarkListByPage(1);
			}
		});
	});
});
function deleteBookMark(obj) {
	var title = $(obj).attr("title");
	if (window.confirm('确定要删除' + title + '吗？')) {
		$.ajax({
			url : basepath + "/bookMark/deleteBookMark.do",
			type : "post",
			dataType : "json",
			data : {
				'title' : title,
			},
			success : function(result) {
				//alert(result.msg);
				findBookMarkListByPage(1);
			}
		})
	}
}
function initPage(page) {

	var totalPage = Math.ceil(page.totalRowNum / page.pageSize);// 总页数
	var maxtotalPage = 10;// 最大显示页数
	var pageNum = page.pageNum;
	// 如果当前页大于总页数 则设置当前页=总页数
	if (pageNum == totalPage) {
		pageNum = totalPage;
	}
	page.pageNum = pageNum-1>1?pageNum-1:1;
	var str = "";
	str+="<td>当前为第"+pageNum+"页&nbsp;<a href='javascript:findBookMarkListByPage("+page.pageNum+")'>上一页</a>&nbsp;";
	if (totalPage < maxtotalPage+1) {
		for (var i = 1; i < totalPage+1; i++) {
			if(i==pageNum){
				str += "<a>"+i+"&nbsp;</a>";
			}else{
				str += "<a href='javascript:findBookMarkListByPage("+i+")'>"+i+"&nbsp;</a>";
			}
		}
		page.pageNum = pageNum+1<totalPage?pageNum+1:totalPage;
		str +="<a href='javascript:findBookMarkListByPage("+page.pageNum+")'>下一页</a></td>";
	} else {
		/**
		 * 当前页-5 当前页+4 
		 */
		 var begin=0;
		 var end = 0;
		 if(pageNum-5>0){
			 /**
			  * 限制每次最多展示10页 算法 展示不同的页数算法也不一样
			  */
			 begin = pageNum-5;
			 end = pageNum+4;
			 if(pageNum>=totalPage-3){
				 begin=totalPage-maxtotalPage+1;
				 end=totalPage;
			 }
			 for(var i = begin;i<end+1;i++){
				 if(i==pageNum){
	  				str+="<a>"+i+"&nbsp;</a>";
					}else{
						str+="<a href='javascript:findBookMarkListByPage("+i+")'>"+i+"&nbsp;</a>";
					}
			 }
			 page.pageNum = pageNum+1<end?pageNum+1:end;
			 str+="<a href='javascript:findBookMarkListByPage("+(page.pageNum)+")'>下一页</a></td>";
		 }else{
			 for(var i = 1;i<maxtotalPage+1;i++){
  				if(i==pageNum){
  					
	  				str+="<a>"+i+"&nbsp;</a>";
					}else{
						str+="<a href='javascript:findBookMarkListByPage("+i+")'>"+i+"&nbsp;</a>";
					}
  			 }
			   page.pageNum = pageNum+1<totalPage?pageNum+1:totalPage;
				 str+="<a href='javascript:findBookMarkListByPage("+page.pageNum+")'>下一页</a></td>";
		 }
		/*for (var i = 0; i < totalPage; i++) {
			str += "<input type='button' value='" + (i + 1)
					+ "' onclick='findBookMarkListByPage(" + (i + 1) + ")'/>";
		}*/
	}
	$("#showPage").html(str);
}