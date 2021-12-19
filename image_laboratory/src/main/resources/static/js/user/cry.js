$("#cry").click(function () {
    var account = document.getElementById("account").value;
    $.ajax({
        type: "POST",
        url:"",
        data:{

        },
        dataType: "json",
        success:function (data) {
        },
        error:function () {
            alert("发送失败，请再次一次")
        }
    })
})