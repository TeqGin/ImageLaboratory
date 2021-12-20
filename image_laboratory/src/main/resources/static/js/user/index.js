showImages(images);
var highlight_part = document.getElementById("image-lib");
highlight_part.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;");

$(document).bind("contextmenu", function(){ return false; });

var stick_time = 800

var current_id = null
var target_directory_id = null
var isDirectory = null
var old_name = null;
/*
$(".right-menu").mousedown(function(params){
    if(params.button === 2){
        current_id = $(this).attr("id");
        var current_name = $(this).attr("name");
        var father = $(this).parent();
        if (current_name === "img"){
            isDirectory = 2;
            current_id = current_id.slice(0,-1)
            old_name = father.find("a").text();
        }else if (current_name === "directory"){
            isDirectory = 1;

            old_name = father.find('p').text();
        }else {
            isDirectory = 3;
        }
        console.log(current_id);
        $(document).bind('contextmenu',function(){return false;});
        $("#contextMenu").css({'top':params.pageY - 80+'px','left':params.pageX - 650+'px'});
        $("#contextMenu").show();
    }
});*/
$("#files-container").on("mousedown",".right-menu",function (params) {
    if(params.button === 2){
        current_id = $(this).attr("id");
        var current_name = $(this).attr("name");
        console.log("current_name", current_name);
        var father = $(this).parent();
        if (current_name === "img"){
            console.log("substring id")
            isDirectory = 2;
            current_id = current_id.slice(0,-1)
            console.log(current_id)
            old_name = father.find("a").text();
        }else if (current_name === "directory"){
            isDirectory = 1;

            old_name = father.find('p').text();
        }else {
            isDirectory = 3;
        }
        console.log(current_id);
        $(document).bind('contextmenu',function(){return false;});
        $("#contextMenu").css({'top':params.pageY - 80+'px','left':params.pageX - 650+'px'});
        $("#contextMenu").show();
    }
});
$("body").click(function(){
    $("#contextMenu").hide();

});


$("#create_folder").click(function () {
    layer.prompt({title: '输入新的文件名字',formType: 0}, function(name, index){
        if (name != null && name != ""){
            var reg = new RegExp('[\\\\/:*?\"<>|]');
            for (var i =0; i <children.length;i++){
                if (children[i].name === name){
                    layer.msg("当前文件夹下已存在同名文件，请另外起一个名字",{icon: 5,time:stick_time});
                    return;
                }else if (name === account || reg.test(name)){
                    layer.msg("文件名非法",{icon: 5,time:stick_time});
                    return;
                }
            }
            $.ajax({
                type:"POST",
                url:"/directory/create",
                data:{
                    name:name
                },
                dataType:"json",
                success:function (data) {
                    location.href = "/user/home?root=0";
                },
                error:function (data) {
                    layer.closeAll();
                    layer.msg("文件已存在",{icon: 5,time:stick_time})
                }
            })
        }
        layer.close(index);
    });
});

/*
$(".folder").click(function () {
    var directoryId=$(this).attr("id");
    $.ajax({
        type:"POST",
        url:"/directory/next",
        data:{
            directoryId:directoryId
        },
        success:function (data) {
            location.href = "/user/home?root=0";
        },
        error:function (data) {
            alert("发生了错误！");
        }
    })
    layer.load(1, {shade: false}); //0代表加载的风格，支持0-2
});*/

$("#files-container").on("click",".folder",function () {
    var directoryId=$(this).attr("id");
    $.ajax({
        type:"POST",
        url:"/directory/next",
        data:{
            directoryId:directoryId
        },
        success:function (data) {
            location.href = "/user/home?root=0";
        },
        error:function (data) {
            alert("发生了错误！");
        }
    })
    layer.load(1, {shade: false}); //0代表加载的风格，支持0-2
});
$("#go_back").click(function () {

    $.ajax({
        type:"POST",
        url:"/directory/back",
        success:function (data) {
            if (data.code === 3){
                layer.msg('已经在根路径了！',{time:stick_time});
            }else {
                location.href = "/user/home?root=0";
                root = 0;
            }
        },
        error:function (data) {
          layer.msg("发生了错误!",{icon:2});
        }
    })

});

/*$(".image").click(function () {
    var id=$(this).attr("id");
    console.log(id);

});*/
$("#files-container").on("click",".image",function () {
    var id=$(this).attr("id");
    console.log(id);
});
$("#files-container").on("click",".images",function () {
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

$("#delete").click(function () {
    layer.confirm('您确定删除该文件吗？如果该文件下还有图片文件则无法删除', {
        btn: ['确认','取消'] //按钮
    }, function(){
        $.ajax({
            type:"POST",
            url:"/directory/delete",
            data:{
                id:current_id,
                isDirectory:isDirectory
            },
            dataType: "json",
            success:function (data) {
                if (data.code === 0){
                    layer.msg("删除成功",{icon:1,time:800})
                    location.href = "/user/home?root=0"
                }else {
                    layer.closeAll()
                    layer.msg(data.message,{icon:2,time:800})
                }
            },
            error:function () {
                layer.closeAll()
                layer.msg("删除失败",{icon:2,time:800})
            }

        })
        layer.load(1, {shade: false}); //0代表加载的风格，支持0-2
    }, function(){
    });

});
$("#rename").click(function () {
    //prompt层
    layer.prompt({title: '输入新的文件夹名字', value:old_name ,formType: 0}, function(name, index){
        var reg = new RegExp('[\\\\/:*?\"<>|]');
        for (var i =0; i <children.length;i++){
            if (children[i].name === name){
                layer.msg("当前文件夹下已存在同名文件夹，请另外起一个名字",{icon: 5,time:stick_time});
                return;
            }else if (name === account || reg.test(name)){
                layer.msg("文件夹名非法",{icon: 5,time:stick_time});
                return;
            }
        }
        $.ajax({
            type:"POST",
            url:"/directory/rename",
            data:{
                id:current_id,
                isDirectory:isDirectory,
                name:name
            },
            dataType:"json",
            success:function (data) {
                console.log("成功");
                if (isDirectory === 1){
                    console.log($("p[name$="+current_id +"]").textContent);
                    $("p[name$="+current_id +"]").text(name);
                }else {
                    document.getElementById(current_id).textContent = name;
                }

            },
            error:function(){
                layer.closeAll();
                console.log("失败")
            }
        });
        layer.close(index);
    });
})
$("#move").click(function () {
    console.log("here")
    $("#model_face").show();
    layui.use('tree', function(){
        var tree = layui.tree;
        //渲染
        var inst1 = tree.render({
            elem: '#file-tree'  //绑定元素
            ,data: [directory_tree],
            accordion:true,
            click: function(obj){
                console.log(obj.data); //得到当前点击的节点数据
                console.log(obj.state); //得到当前节点的展开状态：open、close、normal
                console.log(obj.elem); //得到当前节点元素
                $("#move-directory-name").text(obj.data.title);
                target_directory_id = obj.data.id;
                //console.log(obj.data.children); //当前节点下是否有子节点
            }
        });
    });
})
$("#close").click(function () {
    $("#model_face").hide();
});

$("#submit-move").click(function () {
    console.log("current_id",current_id)
    console.log("target_id",target_directory_id)
    $.ajax({
        type:"POST",
        url:"/directory/move",
        data:{
            src_id:current_id,
            target_id:target_directory_id,
            isDirectory:isDirectory
        },
        dataType:"json",
        success:function (data) {
            layer.msg("移动成功",{icon:1})
            location.href = "/user/home?root=0";
        },
        error:function () {
            layer.closeAll()
            layer.msg("移动失败",{icon:2})
        }
    })
})


function file_change(target) {
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    if (isIE && !$("#doc").files) { // IE浏览器
        var filePath = target.value; // 获得上传文件的绝对路径
        //	alert("文件的绝对路径：" + filePath);
        /**
         * ActiveXObject 对象为IE和Opera所兼容的JS对象
         * 用法：
         *         var newObj = new ActiveXObject( servername.typename[, location])
         *         其中newObj是必选项。返回 ActiveXObject对象 的变量名。
         *        servername是必选项。提供该对象的应用程序的名称。
         *        typename是必选项。要创建的对象的类型或类。
         *        location是可选项。创建该对象的网络服务器的名称。
         *\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         *     Scripting.FileSystemObject 为 IIS 内置组件，用于操作磁盘、文件夹或文本文件，
         *    其中返回的 newObj 方法和属性非常的多
         *    如：var file = newObj.CreateTextFile("C:\test.txt", true) 第二个参表示目标文件存在时是否覆盖
         *    file.Write("写入内容");    file.Close();
         */
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        // GetFile(path) 方法从磁盘获取一个文件并返回。
        var file = fileSystem.GetFile(filePath);
        fileSize = file.Size; // 文件大小，单位：b

    } else { // 非IE浏览器
        //	alert("非IE浏览器");
        fileSize = target.files[0].size;
    }
    //	alert("文件大小:b :" + fileSize);
    var size = fileSize / 1024 / 1024 / 1024;
    if (size > 1 && fileSize!=0) {
        alert("附件不能大于1G或您尚未选择文件");
    }else {
        var f = new FormData(document.getElementById("form_msg"));
        $.ajax({
            type: "POST",
            url: "/user/upload",
            data: f,
            processData:false,
            contentType:false,
            success: function (data) {
                if (data.code === 0){
                    layer.closeAll();
                    layer.msg("上传成功！",{icon:1,time:stick_time})
                    location.href = "/user/home?root=0";
                }else {
                    layer.closeAll();
                    layer.msg(data.message,{icon:2,time:stick_time})
                }

            },
            error: function (data) {
                layer.closeAll();
                layer.msg("上传失败！",{icon:2,time:stick_time})
            }
        })
        layer.load(1, {shade: false}); //0代表加载的风格，支持0-2
    }
}

function downF(id) {
    var r =confirm("确认下载文件吗？")
    if (r === true){
        var form =$("<form>")
        form.attr("style", "display:none")
        form.attr("target", "")
        form.attr("method", "post")
        form.attr("action", "/user/download?id=" + id)
        console.log("download Id:" + id);
        $('body').append(form)
        form.submit();
    }
}

$("#search-icon").click(function () {
    layer.prompt({title: '输入查找关键字', formType: 0}, function(keyword, index){
        $.ajax({
            type:'POST',
            url:"/directory/search",
            data:{
                keyword:keyword
            },
            dataType:"json",
            success:function (data) {
                refreshFies(data.directories, data.images)
                showImages(data.images);
            },
            error:function () {
                layer.msg("查找失败",{icon:2,time:800});
            }
        })
        layer.close(index);
    });
});

$("#asc-type").click(function () {
    $.ajax({
        type:'POST',
        url:"/directory/sort",
        data:{
            way:0
        },
        dataType:"json",
        success:function (data) {
            console.log(data.directories);
            console.log(data.images);
            refreshFies(data.directories, data.images)
            showImages(data.images);
        },
        error:function () {
            layer.msg("排序失败",{icon:2,time:800});
        }
    })
});

$("#desc-type").click(function () {
    $.ajax({
        type:'POST',
        url:"/directory/sort",
        data:{
            way:1
        },
        dataType:"json",
        success:function (data) {
            refreshFies(data.directories, data.images);
            showImages(data.images);
        },
        error:function () {
            layer.msg("排序失败",{icon:2,time:800});
        }
    })
});

function refreshFies(directories,images){
    $("#files-container").empty();
    for (let i = 0; i< directories.length; i++){
        var direcory = "                <div  class=\"folder_container\">\n" +
            "                    <img src=\" /img/folder.png\" class=\"folder right-menu\" id=\""+directories[i].id+"\" name=\"directory\">\n" +
            "                    <p  class=\"folder_name\" name=\""+directories[i].id+"\">"+directories[i].name+"</p>\n" +
            "                </div>";
        $("#files-container").append(direcory);
        console.log(i)
    }
    for (let i = 0; i< images.length; i++){
        var image = "                <div  class=\"folder_container\">\n" +
            "                    <img src=\" /img/temp_image.png\" class=\"images right-menu\" id=\""+images[i].id+"@\" name=\"img\">\n" +
            "                    <br>\n" +
            "                    <a  id=\""+images[i].id+"\" class=\"image\" dowmload=\""+images[i].name+"\" onclick=\"downF([['"+images[i].id+"']])\" >"+images[i].name+"</a>\n" +
            "                </div>";
        $("#files-container").append(image);
    }
}
/*$(".images").click(function () {
    var img = "<img src='" + $(this).attr('src') + "'></img>"
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        shadeClose: true,
        skin: 'yourclass',
        content: img
    });
})*/

// 实现逐个图片进行渲染
function showImages(imagesList) {
    for (let i = 0; i < imagesList.length; i++) {
        if (imagesList[i].type === "image"){
            $.ajax({
                type:'POST',
                url:"/image/image_file",
                data:{
                    id:imagesList[i].id
                },
                dataType:"json",
                success:function (data) {
                    var img = document.getElementById(imagesList[i].id+"@");
                    img.src= "data:image/jpg;base64,"+data.base64;
                },
                error:function () {

                }
            })
        }
    }
}

$("#all_part").click(function () {
    window.location.reload();
});

$("#only_image").click(function () {
    $.ajax({
        type:'POST',
        url:"/directory/only_images",
        dataType:"json",
        success:function (data) {
            refreshFies(data.directories, data.images);
            showImages(data.images);
        },
        error:function () {
            layer.msg("查找失败",{icon:2,time:800});
        }
    })
})