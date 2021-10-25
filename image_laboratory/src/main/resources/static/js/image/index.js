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
        var f = new FormData(document.getElementById("form_msg"))
        $.ajax({
            type: "POST",
            url: "/image/gray",
            data: f,
            processData:false,
            contentType:false,
            success: function (data) {
                //console.log();
            },
            error: function (data) {
                alert("上传失败")
            }
        })
    }
}

$("#ocr").click(function () {
    location.href="/image/ocr";
});

$("#nst").click(function () {
    location.href="/image/change";
});

$("#image-enhance").click(function () {
    location.href="/image/enhance";
});

$("#face").click(function () {
    location.href="/image/catch_face";
});