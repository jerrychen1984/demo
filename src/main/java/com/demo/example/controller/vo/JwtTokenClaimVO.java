package com.demo.example.controller.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonSerialize
@ApiModel(description = "换取令牌返回结果")
public class JwtTokenClaimVO {

    @ApiModelProperty(value = "返回码, 0:成功, 1:邮件尚未通过验证"
            , dataType = "int", example = "0", required = true)
    private int code;

    @ApiModelProperty(value = "错误信息", dataType = "string", example = "成功")
    private String message;

    @ApiModelProperty(value = "JWT令牌", dataType = "string", example = "WE#$%&&**1eLiy245tT")
    private String token;

    public static JwtTokenClaimVO success(String token) {
        return new JwtTokenClaimVO(0, "OK", token);
    }

    public static JwtTokenClaimVO emailNotVerified() {
        return new JwtTokenClaimVO(1, "邮件尚未通过验证", null);
    }
}


