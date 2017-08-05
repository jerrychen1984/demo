package com.demo.example.controller.vo.feeset;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class FeesetListVO {


    @ApiModelProperty(value = "列表", dataType = "list")
    private List<FeesetItemVO> feesets = new ArrayList<>();

    @Data
    @ApiModel
    public static class FeesetItemVO {

        @ApiModelProperty(value = "套餐ID", dataType = "long", example = "1")
        private Long id;

        @ApiModelProperty(value = "套餐标题", dataType = "string", example = "基础套餐")
        private String title;

        @ApiModelProperty(value = "套餐描述", dataType = "string", example = "体验用户套餐")
        private String description;

        @ApiModelProperty(value = "最大H5页面数", dataType = "int", example = "10")
        private Long maxPages;

        @ApiModelProperty(value = "PV限制", dataType = "long", example = "10000")
        private Long maxPageView;

        @ApiModelProperty(value = "有效期(年)", dataType = "int", example = "1")
        private Integer validity;

        @ApiModelProperty(value = "价格", dataType = "double", example = "99.0")
        private Double price;

    }


}
