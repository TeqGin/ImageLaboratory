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
        var file = target.files[0];
        if(window.FileReader) {
            var fr = new FileReader();
            var showimg = document.getElementById('image');
            fr.onloadend = function(e) {
                showimg.src = e.target.result;
            };
            fr.readAsDataURL(file);
            showimg.style.display = 'block';
        }
        $("#word").text("正在识别，请耐心等候");
        $.ajax({
            type: "POST",
            url: "/image/ocr_image",
            data: f,
            processData:false,
            contentType:false,
            success: function (data) {
                console.log(data);
                $("#word").text(data.content)
            },
            error: function (data) {
                alert("上传失败")
            }
        })
    }
}

$("#submit-move").click(function () {
    $("#model_face").hide();
    $.ajax({
        type:"POST",
        url:'/image/show_local_image',
        data: {
            path:current_url
        },
        dataType:"json",
        success: function (data) {
            $("#word").text('识别中，请耐心等候');
            var img = document.getElementById('image');
            img.src= "data:image/jpg;base64,"+data.base64;
        },
        error: function (data) {
            layer.msg("读取云空间图片失败",{icon:2,time:800})
        }
    });
    $.ajax({
        type: "POST",
        url: "/image/ocr_local_image",
        data: {
            path:current_url
        },
        dataType:"json",
        success: function (data) {
            console.log(data);
            $("#word").text(data.content);
            var img = document.getElementById('image');
            img.src= "data:image/jpg;base64," + data.base64;
        },
        error: function (data) {
            layer.msg("上传失败",{icon:2,time:800});
        }
    });
    current_image_id = null;
    current_url = null;
    $("#move-directory-name").text("");
});