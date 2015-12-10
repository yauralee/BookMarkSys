<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	
  	<script type="text/javascript" src="<%=basePath %>js/jquery-1.4.4.min.js"></script>
  	<script type="text/javascript">
  		$(function(){
			paping(20);//传入当前页
  		});
  		function paping(pg_num){
  			var maxPageCount=10;//最大显示页数
  			var pageCount=50;//总页数
  			//如果当前页大于总页数 则设置当前页=总页数
  			if(pg_num==pageCount){
  				pg_num=pageCount;
  			}
  			var option="";
			//小于最大显示页数 直接循环
  			option+="<td>当前为第"+pg_num+"页&nbsp;<a href='javascript:paping("+(pg_num-1>1?pg_num-1:1)+ ")'>上一页</a>&nbsp;";
  			if(pageCount<maxPageCount+1){
  				for(var i = 1;i<pageCount+1;i++){
  					if(i==pg_num){
		  				option+="<a>"+i+"&nbsp;</a>";
  					}else{
  						option+="<a href='javascript:paping("+i+")'>"+i+"&nbsp;</a>";
  					}
	  			}
  				option+="<a href='javascript:paping("+(pg_num+1<pageCount?pg_num+1:pageCount)+")'>下一页</a></td>";
  			}else{
				/**
				 * 当前页-5 当前页+4 
				 */
				 var begin=0;
				 var end = 0;
				 if(pg_num-5>0){
					 /**
					  * 限制每次最多展示10页 算法 展示不同的页数算法也不一样
					  */
					 begin = pg_num-5;
					 end = pg_num+4;
					 if(pg_num>=pageCount-3){
						 begin=pageCount-maxPageCount+1;
						 end=pageCount;
					 }
					 for(var i = begin;i<end+1;i++){
						 if(i==pg_num){
			  				option+="<a>"+i+"&nbsp;</a>";
	  					}else{
	  						option+="<a href='javascript:paping("+i+")'>"+i+"&nbsp;</a>";
	  					}
					 }
					 option+="<a href='javascript:paping("+(pg_num+1<end?pg_num+1:end)+")'>下一页</a></td>";
				 }else{
					 for(var i = 1;i<maxPageCount+1;i++){
		  				if(i==pg_num){
			  				option+="<a>"+i+"&nbsp;</a>";
	  					}else{
	  						option+="<a href='javascript:paping("+i+")'>"+i+"&nbsp;</a>";
	  					}
		  			 }
	  				 option+="<a href='javascript:paping("+(pg_num+1<pageCount?pg_num+1:pageCount)+")'>下一页</a></td>";
				 }
  			}
  			$("#test_tr").html(option);
  		}
  	</script>
  	</head>
  
  <body>
  	<table>
  		<tr id="test_tr"></tr>
  	</table>
  </body>
</html>
