var highlight_part = document.getElementById("personal-info");
highlight_part.setAttribute("style","" +
    "box-shadow: 1px 1px 10px gray;\n" +
    "background-color: #f5f5f5;");

$("#change-password").click(function () {
    location.href="/user/change_password_page";
});
$("#see-email").click(function () {
    location.href="/user/see_email_page";
});
$("#change-phone").click(function () {
    location.href="/user/change_phone_page";
});
$("#change-nickname").click(function () {
    location.href="/user/change_name_page";
});
$("#record").click(function () {
    location.href="/user/login_record_page";
});
$("#cry-account").click(function () {
    location.href="/user/cry_for_account_page";
});
$("#kill-account").click(function () {
    location.href="/user/kill_account_page";
});