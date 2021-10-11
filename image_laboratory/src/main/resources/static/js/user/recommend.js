var highlight_part = document.getElementById("preference");
highlight_part.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;");


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