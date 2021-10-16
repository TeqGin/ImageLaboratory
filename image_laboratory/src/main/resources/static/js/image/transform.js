var current_image_id= null;
var current_url = null;

$("#select-cloud").click(function () {
    console.log("status",status);
    if (status === "-1"){
        layer.msg("请先登陆！",{icon:2,time:800})
    }else{
        console.log("tree!");
        $("#model_face").show();
        layui.use('tree', function(){
            var tree = layui.tree;
            // 渲染
            var inst1 = tree.render({
                elem: '#file-tree'  //绑定元素
                ,data: [directory_tree],
                accordion:true,
                showLine: false,     // 不开启连接线
                click: function(obj){
                    console.log(obj.data); //得到当前点击的节点数据
                    console.log(obj.state); //得到当前节点的展开状态：open、close、normal
                    console.log(obj.elem); //得到当前节点元素
                    if (obj.data.is_image === "1"){
                        $("#move-directory-name").text(obj.data.title);
                        var src = $("#image").attr("src");
                        current_image_id = obj.data.id;
                        current_url = obj.data.path;
                    }
                    // console.log(obj.data.children); //当前节点下是否有子节点
                }
            });
        });
    }
});

$("#close").click(function () {
    $("#model_face").hide();
    current_image_id = null;
    current_url = null;
    $("#move-directory-name").text("");
});

