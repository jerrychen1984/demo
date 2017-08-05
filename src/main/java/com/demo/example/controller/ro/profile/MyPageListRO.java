package com.demo.example.controller.ro.profile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MyPageListRO {

    @ApiModelProperty(value = "第几页", dataType = "int", example = "1")
    private int page;

    @ApiModelProperty(value = "分页大小", dataType = "int", example = "10")
    private int pageSize;

}
