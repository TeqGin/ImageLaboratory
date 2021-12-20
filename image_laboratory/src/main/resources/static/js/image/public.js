var baseString = "";
var hit = document.getElementById("index");
hit.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;\n" +
    "border-radius: 8px;");

$(document).bind("contextmenu", function(){ return false; });

$("#left_part").on("mousedown","#image",function (params) {
    if(params.button === 2){
        $(document).bind('contextmenu',function(){return false;});
        baseString = $(this).attr("src");
        console.log(baseString);
        $("#contextMenu").css({'top':params.pageY - 550 +'px','left':params.pageX - 600+'px'});
        $("#contextMenu").show();
    }
});
$("body").click(function(){
    $("#contextMenu").hide();
});

$("#add").click(function () {
    if (status === "-1"){
        layer.msg("请先登陆！",{icon:2,time:800})
    }else{
        $.ajax({
            type:"POST",
            url:"/image/save_base64",
            data:{
                baseString:baseString
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
    }

})