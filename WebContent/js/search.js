function timeFormat(timeStamp){
  var date = new Date(parseInt(timeStamp));
  return 'created@' + date.getFullYear() + '-' + (date.getMonth()+1) + '-' + (date.getDate()+1);
}
function appendBookmarks(data){
  var searchResult = data.reduce(function(str,item){
    str+="<li class='bookmarkTitle'>"+item.title+"</li>";
    str+="<li class='bookmarkCreated'>"+timeFormat(item.created)+"</li>";
    return str;
  },"");
  $('ul').html(searchResult);
}

$(document).ready(function(){
  $.ajax({
    url:"bookmarks.json",
    type:"get",
    dataType:"json",
    success:function(data){
      appendBookmarks(data);
    }
  })
  function showResult(keywords,bookmarks){
    $("ul").html("");
    var reg = new RegExp(keywords,"ig");
    var selectedData = bookmarks.filter(function(item){
      return reg.test(item.title);
    }).map(function(item){
      item.title = item.title.replace(reg,'<span style="background-color:#D58C87">'+keywords+'</span>');
      return item;
    });
    appendBookmarks(selectedData);
  }

  $("#keywordInput").bind("input propertychange",function(){
     $.ajax({
       url:"bookmarks.json",
       type:"get",
       dataType:"json",
       success:function(data){
         var inputWords = $('#keywordInput').val();
         showResult(inputWords,data);
       }
     })
  });

})
