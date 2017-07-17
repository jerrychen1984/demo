package com.demo.example.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserInfoVO {

    @ApiModelProperty(dataType = "long", example = "1")
    private Long id;

    @ApiModelProperty(dataType = "string", example = "zhcen")
    private String displayName;

    @ApiModelProperty(dataType = "string", example = "zhcen@163.com")
    private String email;

    @ApiModelProperty(dataType = "boolean", example = "0")
    private boolean emailVerified;

}
