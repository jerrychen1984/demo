package com.demo.example.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class MailVerifyVO {

    public static final int SUCCESS = 0;
    public static final int KEY_EXPIRED = 1;
    public static final int KEY_INVALID = 2;

    @ApiModelProperty(value = "返回码,0:成功, 1:验证过期, 2:无效的验证"
            , dataType = "int", example = "0", required = true)
    private int code;

    @ApiModelProperty(value = "错误信息"
            , dataType = "string", example = "成功")
    private String message = "";

    public static MailVerifyVO expired() {
        return new MailVerifyVO(KEY_EXPIRED, "验证已过期、请重新验证");
    }

    public static MailVerifyVO success() {
        return new MailVerifyVO(SUCCESS, "验证通过");
    }

    public static MailVerifyVO invalid() {
        return new MailVerifyVO(KEY_INVALID, "无效验证");
    }

}
