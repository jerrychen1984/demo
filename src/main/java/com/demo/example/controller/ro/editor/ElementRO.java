package com.demo.example.controller.ro.editor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ElementVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@ApiModel
public class ElementRO {

    @ApiModelProperty(value = "元素id")
    private String elementId;

    @ApiModelProperty(value = "图片地址")
    private String pic;

    @ApiModelProperty(value = "图片高")
    private String height = "0";

    @ApiModelProperty(value = "图片宽")
    private String width = "0";

    @ApiModelProperty(value = "标题", example = "")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "跳转地址")
    private String jumpUrl;

    @ApiModelProperty(value = "价格")
    private String price = "0";

    @ApiModelProperty(value = "价格文案")
    private String priceText;

    @ApiModelProperty(value = "定位")
    private String position;

    @ApiModelProperty(value = "排序字段")
    private String order = "0";
}
