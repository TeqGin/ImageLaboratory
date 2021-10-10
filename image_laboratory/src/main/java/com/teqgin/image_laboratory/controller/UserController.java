package com.teqgin.image_laboratory.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.SecureUtil;
import com.teqgin.image_laboratory.helper.CodeStatus;
import com.teqgin.image_laboratory.domain.Directory;
import com.teqgin.image_laboratory.domain.Img;
import com.teqgin.image_laboratory.domain.User;
import com.teqgin.image_laboratory.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ImgService imgService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /**
     * 验证用户是否合法
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/verify")
    @ApiOperation("登陆验证")
    @ResponseBody
    public ResponseEntity<?> verify(@RequestBody User user,HttpServletRequest request){
        // 验证用户
        boolean legal = userService.isUserLegal(user);
        var body = new HashMap<String, Object>();
        if (legal){
            // 获得user完整信息
            user = userService.getUser(user.getAccount());
            // 抹除密码，防止网络攻击
            user.setPassword("");
            userService.setCurrentUser(request, user);
            userService.initDirectory(request);
            body.put("code", CodeStatus.SUCCEED);
            body.put("message","登陆成功");
            return ResponseEntity.ok(body);
        }
        body.put("code", CodeStatus.DATA_ERROR);
        body.put("message","登陆失败，账号或密码错误");
        return ResponseEntity.ok(body);
    }

    /**
     * 用户注册验证，
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/veri_sign_up")
    @ResponseBody
    public ResponseEntity<?> verifySignUp(@RequestBody User user,HttpServletRequest request){
        user.setPassword(SecureUtil.md5(user.getPassword()));
        boolean exists = userService.isUserExists(user.getAccount());
        var body = new HashMap<String, Object>();
        if (!exists){
            user.setId(IdUtil.getSnowflake().nextIdStr());
            userService.addUser(user);
            body.put("code", CodeStatus.SUCCEED);
            body.put("message","注册成功!");
            return ResponseEntity.ok(body);
        }
        body.put("code", CodeStatus.DATA_ERROR);
        body.put("message","注册失败。请再试一次");
        return ResponseEntity.ok(body);
    }


    /**
     * 忘记密码
     * @param user
     * @return
     */
    @PostMapping("/verify_forget")
    public ResponseEntity<?> verifyForget(@RequestBody User user){
        int code = userService.changePassword(user);
        var body = new HashMap<String, Object>();
        body.put("code", code);
        return ResponseEntity.ok(body);
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @PostMapping("/send_verify_code")
    public ResponseEntity<?> sendCode(@RequestParam String email){
        int code = mailService.sendMail(email);
        var body = new HashMap<String, Object>();
        body.put("code", code);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(HttpServletRequest request, @RequestParam("doc") MultipartFile doc) throws IOException {
        var body = new HashMap<String, Object>();
        userService.upload(doc,request);
        body.put("code",CodeStatus.SUCCEED);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/download")
    public ResponseEntity<?> download(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("id")String id) throws IOException {
        userService.download(response, id);
        var body = new HashMap<String,Object>();
        body.put("code", CodeStatus.SUCCEED);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/sign_out")
    public String quit(HttpServletRequest request){
        // 清除session中缓存的数据
        userService.deployAllInfo(request);
        return "/user/login";
    }

    @GetMapping("/info")
    public String info(){
        return "/user/info";
    }
    @GetMapping("/recommend")
    public String recommend(Model model, HttpServletRequest request) {
        List<String> urls = new ArrayList<>();
        try {
            urls = imgService.recommendImage(request);
            log.info(String.format("抓取到%d张图片", urls.size()));
        } catch (NullPointerException npe) {
            log.error("抓取图片失败");
        }
        model.addAttribute("urls", urls);
        return "/user/recommend";
    }
    @GetMapping("/data_analyze")
    public String data_analyze(){
        return "/user/data_analyze";
    }

    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request, @RequestParam("root") int root){
        if (root == 1){
            userService.initDirectory(request);
        }
        String parentId = directoryService.getCurrentDirectory(request).getId();
        List<Directory> directoryList = directoryService.getChildDirectory(parentId);
        List<Img> imgList = imgService.getImagesByParentId(parentId);
        model.addAttribute("children", directoryList);
        model.addAttribute("images", imgList);
        model.addAttribute("total", "共"+(directoryList.size() + imgList.size()) + "项");
        model.addAttribute("tree", directoryService.getTree(directoryService.getRootDirectory(userService.getCurrentUser(request).getAccount())));
        return "/user/home";
    }

    @GetMapping("reset_path")
    @ResponseBody
    public ResponseEntity<?> reset(HttpServletRequest request){
        //重置用户的文件夹路径
        userService.initDirectory(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/forget")
    public String forget(){
        return "/user/forget";
    }

    @GetMapping("/sign_up")
    public String signUp(){
        return "/user/sign_up";
    }

    @GetMapping("/change_name_page")
    public String changeNamePage(){
        return "/user/info_children/change_name";
    }
    @GetMapping("/change_password_page")
    public String changePassword(){
        return "/user/info_children/change_password";
    }
    @GetMapping("/change_phone_page")
    public String changePhone(){
        return "/user/info_children/change_phone";
    }
    @GetMapping("/cry_for_account_page")
    public String cryForAccountPage(){
        return "/user/info_children/cry_for_account";
    }
    @GetMapping("/kill_account_page")
    public String killAccountPage(){
        return "/user/info_children/kill_account";
    }
    @GetMapping("/login_record_page")
    public String loginRecordPage(){
        return "/user/info_children/login_record";
    }
    @GetMapping("/see_email_page")
    public String seeEmail(){
        return "/user/info_children/see_email";
    }

}
