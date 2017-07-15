package com.demo.example.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailVerifyResendVO {

    public static final int SUCCESS = 0;
    public static final int KEY_VERIFIED = 1;
    public static final int KEY_USER_NOT_FOUND = 2;
    public static final int KEY_SEND_EMAIL_FAILED = 3;


    @ApiModelProperty(value = "返回码,0:成功, 1:已验证过, 2:用户不存在, 3:发送邮件失败"
            , dataType = "int", example = "0", required = true)
    private int code;

    @ApiModelProperty(value = "错误信息"
            , dataType = "string", example = "成功")
    private String message = "";

    public static MailVerifyResendVO success() {
        return new MailVerifyResendVO(SUCCESS, "发送成功");
    }

    public static MailVerifyResendVO verified() {
        return new MailVerifyResendVO(KEY_VERIFIED, "已通过验证");
    }

    public static MailVerifyResendVO userNotFound() {
        return new MailVerifyResendVO(KEY_USER_NOT_FOUND, "用户不存在");
    }

    public static MailVerifyResendVO emailSendFailed() {
        return new MailVerifyResendVO(KEY_SEND_EMAIL_FAILED, "邮件发送失败");
    }
}
