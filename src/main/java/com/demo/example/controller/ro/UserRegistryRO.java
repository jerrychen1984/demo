package com.demo.example.controller.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UserRegistryRO {

    @NotNull @Email
    @ApiModelProperty(value = "电子邮箱"
            , dataType = "string", example = "zhcen@163.com", required = true)
    private String username; // email
    @NotNull
    @ApiModelProperty(value = "密码"
            , dataType = "string", example = "********", required = true)
    private String password;
    @NotNull
    @ApiModelProperty(value = "昵称"
            , dataType = "string", example = "小虾米", required = true)
    private String displayName;

    @ApiModelProperty(value = "邀请码,可选"
            , dataType = "string", example = "2F%$23FtsX=")
    private String inviteCode;

}

