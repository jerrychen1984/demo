package com.demo.example.controller.vo.profile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyPageListVO {

    @ApiModelProperty(value = "总数", dataType = "int", example = "20")
    private int count;

    @ApiModelProperty(value = "第几页", dataType = "int", example = "1")
    private int page;

    @ApiModelProperty(value = "分页大小", dataType = "int", example = "10")
    private int pageSize;

    @ApiModelProperty(value = "页面列表", dataType = "list")
    private List<PageVO> items = new ArrayList<>();

    @Data
    @ApiModel
    public static class PageVO {

        @ApiModelProperty(value = "总数", dataType = "int", example = "20")
        private Long id;

        @ApiModelProperty(value = "页面名称", dataType = "int", example = "20")
        private String name;

        @ApiModelProperty(value = "总数", dataType = "int", example = "20")
        private String linkUrl;

        @ApiModelProperty(value = "总数", dataType = "int", example = "20")
        private Long gmtModified;

        @ApiModelProperty(value = "总数", dataType = "int", example = "20")
        private Integer status;
    }

}
