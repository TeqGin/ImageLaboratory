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
    layer.prompt({title: '????????????????????????',formType: 0}, function(name, index){
        if (name != null && name != ""){
            var reg = new RegExp('[\\\\/:*?\"<>|]');
            for (var i =0; i <children.length;i++){
                if (children[i].name === name){
                    layer.msg("??????????????????????????????????????????????????????????????????",{icon: 5,time:stick_time});
                    return;
                }else if (name === account || reg.test(name)){
                    layer.msg("???????????????",{icon: 5,time:stick_time});
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
                    layer.msg("???????????????",{icon: 5,time:stick_time})
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
            alert("??????????????????");
        }
    })
    layer.load(1, {shade: false}); //0??????????????????????????????0-2
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
            alert("??????????????????");
        }
    })
    layer.load(1, {shade: false}); //0??????????????????????????????0-2
});
$("#go_back").click(function () {

    $.ajax({
        type:"POST",
        url:"/directory/back",
        success:function (data) {
            if (data.code === 3){
                layer.msg('????????????????????????',{time:stick_time});
            }else {
                location.href = "/user/home?root=0";
                root = 0;
            }
        },
        error:function (data) {
          layer.msg("???????????????!",{icon:2});
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
    layer.confirm('?????????????????????????????????????????????????????????????????????????????????', {
        btn: ['??????','??????'] //??????
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
                    layer.msg("????????????",{icon:1,time:800})
                    location.href = "/user/home?root=0"
                }else {
                    layer.closeAll()
                    layer.msg(data.message,{icon:2,time:800})
                }
            },
            error:function () {
                layer.closeAll()
                layer.msg("????????????",{icon:2,time:800})
            }

        })
        layer.load(1, {shade: false}); //0??????????????????????????????0-2
    }, function(){
    });

});
$("#rename").click(function () {
    //prompt???
    layer.prompt({title: '???????????????????????????', value:old_name ,formType: 0}, function(name, index){
        var reg = new RegExp('[\\\\/:*?\"<>|]');
        for (var i =0; i <children.length;i++){
            if (children[i].name === name){
                layer.msg("?????????????????????????????????????????????????????????????????????",{icon: 5,time:stick_time});
                return;
            }else if (name === account || reg.test(name)){
                layer.msg("??????????????????",{icon: 5,time:stick_time});
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
                console.log("??????");
                if (isDirectory === 1){
                    console.log($("p[name$="+current_id +"]").textContent);
                    $("p[name$="+current_id +"]").text(name);
                }else {
                    document.getElementById(current_id).textContent = name;
                }

            },
            error:function(){
                layer.closeAll();
                console.log("??????")
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
        //??????
        var inst1 = tree.render({
            elem: '#file-tree'  //????????????
            ,data: [directory_tree],
            accordion:true,
            click: function(obj){
                console.log(obj.data); //?????????????????????????????????
                console.log(obj.state); //????????????????????????????????????open???close???normal
                console.log(obj.elem); //????????????????????????
                $("#move-directory-name").text(obj.data.title);
                target_directory_id = obj.data.id;
                //console.log(obj.data.children); //?????????????????????????????????
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
            layer.msg("????????????",{icon:1})
            location.href = "/user/home?root=0";
        },
        error:function () {
            layer.closeAll()
            layer.msg("????????????",{icon:2})
        }
    })
})


function file_change(target) {
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    if (isIE && !$("#doc").files) { // IE?????????
        var filePath = target.value; // ?????????????????????????????????
        //	alert("????????????????????????" + filePath);
        /**
         * ActiveXObject ?????????IE???Opera????????????JS??????
         * ?????????
         *         var newObj = new ActiveXObject( servername.typename[, location])
         *         ??????newObj????????????????????? ActiveXObject?????? ???????????????
         *        servername?????????????????????????????????????????????????????????
         *        typename???????????????????????????????????????????????????
         *        location????????????????????????????????????????????????????????????
         *\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         *     Scripting.FileSystemObject ??? IIS ???????????????????????????????????????????????????????????????
         *    ??????????????? newObj ???????????????????????????
         *    ??????var file = newObj.CreateTextFile("C:\test.txt", true) ???????????????????????????????????????????????????
         *    file.Write("????????????");    file.Close();
         */
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        // GetFile(path) ?????????????????????????????????????????????
        var file = fileSystem.GetFile(filePath);
        fileSize = file.Size; // ????????????????????????b

    } else { // ???IE?????????
        //	alert("???IE?????????");
        fileSize = target.files[0].size;
    }
    //	alert("????????????:b :" + fileSize);
    var size = fileSize / 1024 / 1024 / 1024;
    if (size > 1 && fileSize!=0) {
        alert("??????????????????1G????????????????????????");
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
                    layer.msg("???????????????",{icon:1,time:stick_time})
                    location.href = "/user/home?root=0";
                }else {
                    layer.closeAll();
                    layer.msg(data.message,{icon:2,time:stick_time})
                }

            },
            error: function (data) {
                layer.closeAll();
                layer.msg("???????????????",{icon:2,time:stick_time})
            }
        })
        layer.load(1, {shade: false}); //0??????????????????????????????0-2
    }
}

function downF(id) {
    var r =confirm("????????????????????????")
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
    layer.prompt({title: '?????????????????????', formType: 0}, function(keyword, index){
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
                layer.msg("????????????",{icon:2,time:800});
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
            layer.msg("????????????",{icon:2,time:800});
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
            layer.msg("????????????",{icon:2,time:800});
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

// ??????????????????????????????
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
            layer.msg("????????????",{icon:2,time:800});
        }
    })
})

$("#share").click(function () {
    $.ajax({
        type:'POST',
        url:"/image/make_public",
        dataType:"json",
        data:{
            id:current_id,
        },
        success:function (data) {
            if (data.code === 0){
                layer.msg("????????????!",{icon:1,time:800});
            }else if (data.code === 3){
                layer.msg("???????????????????????????",{icon:1,time:800});
            }
        },
        error:function () {
            layer.msg("????????????",{icon:2,time:800});
        }
    })
});
$("#refresh").click(function () {
    window.location.reload();
});