$("#submit").click(function () {
    var account = document.getElementById("account").value;
    var psd = document.getElementById("pwd").value;
    var verfi_pwd = document.getElementById("veri_pwd").value;
    var name = document.getElementById("name").value;
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    if (account == "" || psd == "" || verfi_pwd == "" ||name ==""){
        alert("昵称和账号密码不能为空")
    }else if (!reg.test(account)){
        alert("请输入正确的邮箱！")
    }else if (psd.length < 7){
        alert("密码不能小于7位")
    }else if(psd != verfi_pwd){
        alert("两次输入的密码不一致")
    }else{
        var json = {
            "account":account,
            "password":psd,
            "name": name
        };
        $.ajax({
            type:"POST",
            url:"/user/veri_sign_up",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(json),
            dataType:"json",
            success:function (data) {
                alert("注册成功！")
                location.href="/user/login"
            },
            error:function (data) {
                alert("注册失败,该账号可能已被注册或由于网络波动出错，请再试一次");
                javascript:__nc.reset();
                $("#submit").prop("disabled", "disabled")
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
            $("#submit").prop("disabled", "")
        }
    }
var nc = new noCaptcha(NC_Opt)
nc.upLang('cn', {
    _startTEXT: "请按住滑块，拖动到最右边",
    _yesTEXT: "验证通过",
    _error300: "哎呀，出错了，点击<a href=\"javascript:__nc.reset()\">刷新</a>再来一次",
    _errorNetwork: "网络不给力，请<a href=\"javascript:__nc.reset()\">点击刷新</a>",
})