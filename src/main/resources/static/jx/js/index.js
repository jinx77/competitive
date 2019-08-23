// var url = "http://zxq.mynatapp.cc";
//var url = "http://xinzuo.jiecms.cn:8081";

var url = "http://localhost:8081";
var hrefUrl = '';

var AuthorizationUrl = sessionStorage.getItem("AuthorizationUrl");
if(!AuthorizationUrl){
  if(window.location.href.split("/")[window.location.href.split("/").length-1] != 'login.html'){
    window.location.href = "login.html";
  }
}

// ---
if(sessionStorage.getItem("userName") == 'admin'){
}else{
  $(".setUp").hide();
}
// ---

$(".wrap_header s").css({"cursor":"pointer"})
$(".wrap_header").css({"height":"60px"})

$("input").css({"-webkit-user-select": "text"})
$(".ts").hide();


if(sessionStorage.getItem("title")){
  $("title").html(sessionStorage.getItem("title"));
}

// -------------------------------------------
function acFun(el) {
	for (var i = 0; i < $(el).length; i++) {
		var href = $(el).eq(i).attr("href");
		href = hrefUrl + href;
		$(el).eq(i).attr({"href": href});
	}
}
acFun('.ac');


// -------------------------------------------
function GetRequest2(variable){
    var query = window.location.search.substring(1);
   var vars = query.split("&");
   for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return decodeURI(pair[1]);}
   }
   return(false);
}

// -------------------------------------------
var scfArr = [];
function getSCF(callBack) {
  scfArr = [];
  var params = JSON.stringify({
    "current": 1,
    "size": 200
  });
  $.ajax({
    url: url+'/company-classify/selectClassifyList',
    type:'POST',
    dataType: 'JSON',
    data: params,
    contentType :'application/json;charset=utf-8',
    headers:
    {
        Authorization: AuthorizationUrl,
    },
    success: function(res) {
      console.log(res.data.dataList);
      scfArr = res.data.dataList
      callBack();
    },
    error: function(){
      console.log('error');
    }
  });
}


// -------------------------------------------
function setUp(val) {
  var params = JSON.stringify({
    "userId": val
  });
  $.ajax({
    url: url+'/user/selectUser',
    type:'POST',
    dataType: 'JSON',
    data: params,
    contentType :'application/json;charset=utf-8',
    headers:
    {
        Authorization: AuthorizationUrl,
    },
    success: function(res) {
      console.log(res.data);
      $(".initTitle").html(res.data.titleName);
    },
    error: function(){
      console.log('error');
    }
  });
}
if(sessionStorage.getItem("userId")){
  setUp(sessionStorage.getItem("userId"));
}

$(document).on('click', '.wrap_header s', function(){
    window.location.href = "login.html";
    sessionStorage.clear();
});

$.ajax({
  url: url+'/user/verify',
  type:'POST',
  dataType: 'JSON',
  // data: params,
  contentType :'application/json;charset=utf-8',
  headers:
  {
      Authorization: AuthorizationUrl,
  },
  success: function(res) {
    console.log(res);
    if(res.code == 401){
      window.location.href = "login.html";
      sessionStorage.clear();
    }
  },
  error: function(){
    console.log('error');
  }
});