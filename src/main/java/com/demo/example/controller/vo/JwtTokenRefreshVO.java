package com.demo.example.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel
public class JwtTokenRefreshVO {

    @ApiModelProperty(value = "刷新后的JWT令牌"
            , dataType = "string", example = "WE#$%&&**1eLiy245tT", required = true)
    private String token;

}
