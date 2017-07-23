package com.demo.example.controller.vo.editor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ModelTitleRO
 *
 * @author xuxiang
 * @since 17/7/23
 */
@Data
@ApiModel
public class ModelTitleVO {

    @ApiModelProperty(value = "标题样式")
    private String titleStyle;

    @ApiModelProperty(value = "主标题")
    private String mainTitle;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "查看更多")
    private String moreLink;

    @ApiModelProperty(value = "标题状态 1:上线 2:隐藏")
    private String titleStatus;

    @ApiModelProperty(value = "标题icon")
    private String titleIcon;
}
