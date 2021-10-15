var highlight_part = document.getElementById("preference");
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

$("#add").click(function () {
    layer.confirm('您确定把该图片加入云空间吗吗？', {
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


$.ajax({
    url:"/user/spider",
    type:"POST",
    success:function (data) {
        console.log(data.urls)
        $("#loading-container").remove();
        for (let i = 0; i< data.urls.length; i++){
            var image = "                    <div  class=\"folder_container\">\n" +
                "                        <img src=\""+ data.urls[i]+"\" class=\"images right-menu\"name=\"img\">\n" +
                "                        <br>\n" +
                "                    </div>";
            $("#images-frame").append(image);
        }
    },
    error:function () {
        layer.msg("获取图片失败",{icon:2})
    }
});