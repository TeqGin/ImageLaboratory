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


$("#sub").click(function () {
    var account = document.getElementById("account").value;
    var psd = document.getElementById("password").value;
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    if (!reg.test(account)){
        alert("请输入正确的邮箱");
    }else {
        var json = {
            "account":account,
            "password":psd
        };
        $.ajax({
            type:"POST",
            url:"/user/verify",
            contentType: "application/json; charset=utf-8",
            data:JSON.stringify(json),
            dataType:"json",
            success:function (data) {
                layer.msg("登陆成功",{icon:1})
                location.href="/user/home?root=0";
            },
            error:function (data) {
                alert("账号或密码错误");
                javascript:__nc.reset();
                $("#sub").prop("disabled", "disabled");
            }
        })
    }
})

$("#sign_up").click(function () {
    location.href = "/user/sign_up"
})

$("#forget").click(function () {
    location.href = "/user/forget"
})