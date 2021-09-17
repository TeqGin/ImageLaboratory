$("#send_verify_code").click(function () {
    var account = document.getElementById("account").value
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/

    if (account == ""){
        alert("请先输入账号（邮箱）")
    }else if(!reg.test(account)){
        alert("请输入正确的邮箱")
    }else {
        alert("已处理该请求，请注意查收")
        $.ajax({
            type: "POST",
            url: "/user/send_verify_code",
            data:{
                email:account
            },
            dataType: "json",
            success:function (data) {
            },
            error:function () {
                alert("发送失败，请再次一次")
            }
        })
    }
})

$("#sub").click(function () {
    var account = document.getElementById("account").value
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    var pwd = document.getElementById("password").value
    var verify_pwd = document.getElementById("verify_password").value
    var verify_code = document.getElementById("verify_code").value
    if (!reg.test(account)){
        alert("请输入正确的邮箱")
    }else if (pwd.length < 7){
        alert("密码不能小于7位")
    }else if (pwd != verify_pwd){
        alert("两次输入的密码不一致!请重新输入")
    }else if (verify_code == ""){
        alert("请输入验证码")
    }else {
        var json = {
            "account":account,
            "password":pwd,
            "verifyCode":verify_code
        };
        $.ajax({
            type: "POST",
            url: "/user/verify_forget",
            contentType: "application/json; charset=utf-8",
            data:JSON.stringify(json),
            success:function (data) {
                if(data.code == 0){
                    alert("修改成功！");
                    location.href = "/user/login";
                }else {
                    alert("修改失败");
                    javascript:__nc.reset();
                    $("#sub").prop("disabled", "disabled");
                }
            },
            error:function (data) {
                alert("修改失败，请检查邮箱或验证码是否输入正确！");
                javascript:__nc.reset();
                $("#sub").prop("disabled", "disabled");
            }
        })
    }
})





var nc_token = ["FFFF0N0000000000983E", (new Date()).getTime(), Math.random()].join(':');
var NC_Opt =
    {
        renderTo: "#your-dom-id",
        appkey: "FFFF0N0000000000983E",
        scene: "nc_login",
        token: nc_token,
        customWidth: 300,
        trans:{"key1":"code0"},
        elementID: ["usernameID"],
        is_Opt: 0,
        language: "cn",
        isEnabled: true,
        timeout: 3000,
        times:5,
        apimap: {
            // 'analyze': '//a.com/nocaptcha/analyze.jsonp',
            // 'get_captcha': '//b.com/get_captcha/ver3',
            // 'get_captcha': '//pin3.aliyun.com/get_captcha/ver3'
            // 'get_img': '//c.com/get_img',
            // 'checkcode': '//d.com/captcha/checkcode.jsonp',
            // 'umid_Url': '//e.com/security/umscript/3.2.1/um.js',
            // 'uab_Url': '//aeu.alicdn.com/js/uac/909.js',
            // 'umid_serUrl': 'https://g.com/service/um.json'
        },
        callback: function (data) {
            $("#sub").prop("disabled", "")
        }
    }
var nc = new noCaptcha(NC_Opt)
nc.upLang('cn', {
    _startTEXT: "请按住滑块，拖动到最右边",
    _yesTEXT: "验证通过",
    _error300: "哎呀，出错了，点击<a href=\"javascript:__nc.reset()\">刷新</a>再来一次",
    _errorNetwork: "网络不给力，请<a href=\"javascript:__nc.reset()\">点击刷新</a>",
})
