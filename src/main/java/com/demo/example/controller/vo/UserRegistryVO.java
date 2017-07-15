package com.demo.example.controller.vo;

import com.demo.example.data.service.exception.InvalidParamsException;
import com.demo.example.data.service.exception.InviteCodeNotFoundException;
import com.demo.example.data.service.exception.InviteCodeWasUsedException;
import com.demo.example.data.service.exception.UserNameExistsException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel
@AllArgsConstructor
public class UserRegistryVO {

    public static final int SUCCESS = 0;
    public static final int KEY_USER_EXISTS = 1;
    public static final int KEY_INVITE_CODE_NOT_FOUND = 2;
    public static final int KEY_INVITE_CODE_USED = 3;
    public static final int KEY_INVALID_PARAMS = 4;
    public static final int KEY_UNKNOWN_ERROR = 5;


    @ApiModelProperty(value = "返回码,0:成功, 1:用户已存在, 2:无效邀请码, 3:邀请码已被使用, 4:参数错误, 5:未知错误"
            , dataType = "int", example = "0", required = true)
    private int code;

    @ApiModelProperty(value = "错误信息"
            , dataType = "string", example = "成功")
    private String error = "";


    public static UserRegistryVO success() {
        return new UserRegistryVO(SUCCESS, "注册成功");
    }

    public static UserRegistryVO error(Exception e) {
        if (e instanceof UserNameExistsException) {
            return new UserRegistryVO(KEY_USER_EXISTS, e.getMessage());
        } else if (e instanceof InviteCodeNotFoundException) {
            return new UserRegistryVO(KEY_INVITE_CODE_NOT_FOUND, e.getMessage());
        } else if (e instanceof InviteCodeWasUsedException) {
            return new UserRegistryVO(KEY_INVITE_CODE_USED, e.getMessage());
        } else if (e instanceof InvalidParamsException) {
            return new UserRegistryVO(KEY_INVALID_PARAMS, e.getMessage());
        } else {
            return new UserRegistryVO(KEY_UNKNOWN_ERROR, e.getMessage());
        }
    }




}
