package com.demo.example.controller;

import com.demo.example.controller.misc.EmailVerifyToken;
import com.demo.example.controller.ro.UserRegistryRO;
import com.demo.example.controller.vo.UserInfoVO;
import com.demo.example.controller.vo.UserRegistryVO;
import com.demo.example.controller.vo.MailVerifyResendVO;
import com.demo.example.controller.vo.MailVerifyVO;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.AuthService;
import com.demo.example.data.service.EmailService;
import com.demo.example.utils.CryptoUtils;
import com.demo.example.utils.JSONUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.nutz.dao.Cnd;
import org.nutz.dao.FieldFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Api(value = "用户注册API", tags = "注册", description = "用户注册服务")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Repository repository;
    @Autowired
    private AuthService authService;
    @Autowired
    private EmailService emailService;
    @Value("${email.verify.url}")
    private String emailVerifyUrl;


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/me"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "获取当前用户信息")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "验证错误"),
            @ApiResponse(code = 404, message = "用户不存在"),
            @ApiResponse(code = 200, message = "用户信息", response = UserInfoVO.class)
    })
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // email
        String username = (String) authentication.getPrincipal();
        User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        if (user != null) {
            UserInfoVO userInfo = new UserInfoVO();
            userInfo.setId(user.getId());
            userInfo.setEmail(user.getUsername());
            userInfo.setEmailVerified(user.isEmailVerified());
            userInfo.setDisplayName(user.getDisplayName());
            return ResponseEntity.ok(userInfo);
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/register"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "注册用户")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "用户名或密码错误"),
            @ApiResponse(code = 200, message = "Token", response = UserRegistryVO.class)
    })
    public UserRegistryVO registerUser(@RequestBody UserRegistryRO registry) {

        try {
            authService.register(registry);
        } catch (Exception e) {
            return UserRegistryVO.error(e);
        }

        return UserRegistryVO.success();
    }

    @RequestMapping(value = "/verify-email"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "验证邮件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token", response = MailVerifyVO.class)
    })
    public MailVerifyVO verifyEmail(@RequestParam("key") String key) {

        try {
            final String content = CryptoUtils.decrypt(key);
            EmailVerifyToken token = JSONUtils.fromJsonString(content, new TypeReference<EmailVerifyToken>(){});
            if (token.getExpiresAt() <= System.currentTimeMillis()) {
                return MailVerifyVO.expired();
            }

            final User user = repository.fetch(User.class, Cnd.where("username", "=", token.getEmail()));
            user.setEmailVerified(true);
            if (repository.update(user, FieldFilter.create(User.class, "emailVerified")) > 0) {
                return MailVerifyVO.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MailVerifyVO.invalid();
    }

    @RequestMapping(value = "/resend-verify-email"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "重发验证邮件")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Token", response = MailVerifyResendVO.class)
    })
    public MailVerifyResendVO resendVerifyEmail(@RequestParam("email") String email) {

        final User user = repository.fetch(User.class, Cnd.where("username", "=", email));
        if (null == user) {
            return MailVerifyResendVO.userNotFound();
        }

        if (user.isEmailVerified()) {
            return MailVerifyResendVO.verified();
        }

        try {
            EmailVerifyToken key = new EmailVerifyToken(email, System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
            String encodedKey = CryptoUtils.encrypt(JSONUtils.toJsonString(key));
            if (emailService.sendVerifyEmail(user, String.format(emailVerifyUrl, encodedKey))) {
                return MailVerifyResendVO.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MailVerifyResendVO.emailSendFailed();
    }

}





