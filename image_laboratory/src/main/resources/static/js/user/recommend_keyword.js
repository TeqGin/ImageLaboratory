var highlight_part = document.getElementById("preference-keyword");
highlight_part.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;");

$(document).bind("contextmenu", function(){ return false; });

var current_id = null
var current_url = null
$("#images-frame").on("mousedown",".right-menu",function(params){
    if(params.button === 2){
        current_url = $(this).attr("src");
        console.log("current_url",current_url);
        $(document).bind('contextmenu',function(){return false;});
        $("#contextMenu").css({'top':params.pageY - 80+'px','left':params.pageX - 650+'px'});
        $("#contextMenu").show();
    }
});
$("body").click(function(){
    $("#contextMenu").hide();

})

$("#search-icon").click(function () {
    layer.prompt({title: '输入查找关键字', formType: 0}, function(keyword, index){
        $.ajax({
            url:"/user/spider_keyword",
            type:"POST",
            data:{
              keyword:keyword
            },
            success:function (data) {
                layer.closeAll();
                for (let i = 0; i< data.urls.length; i++){
                    var image = "                    <div  class=\"folder_container\">\n" +
                        "                        <img src=\""+ data.urls[i]+"\" class=\"images right-menu\"name=\"img\">\n" +
                        "                        <br>\n" +
                        "                    </div>";
                    $("#images-frame").append(image);
                }
            },
            error:function () {
                layer.closeAll();
                layer.msg("获取图片失败",{icon:2})
            }
        });
        layer.closeAll();
        layer.load(1, {shade: false});
    });
});

$("#add").click(function () {
    layer.confirm('您确定把该图片加入云空间吗？', {
        btn: ['确认','取消'] //按钮
    }, function(name,index){
        console.log("current_url",current_url);
        $.ajax({
            type:"POST",
            url:"/user/add_to_cloud",
            data:{
                url:current_url
            },
            dataType: "json",
            success:function (data) {
                layer.closeAll();
                layer.msg("添加成功",{icon:1})
            },
            error:function () {
                layer.closeAll();
                layer.msg("添加失败",{icon:2})
            }

        })
        layer.closeAll();
        // layer.load(0, {shade: false}); 0代表加载的风格，支持0-2
        layer.load(1, {shade: false});
    }, function(){
    });
})

$("#images-frame").on("click",".images",function () {
    var img = "<img src='" + $(this).attr('src') + "'></img>"
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        shadeClose: true,
        skin: 'yourclass',
        content: img
    });
});