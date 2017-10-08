package com.demo.example.controller.vo.editor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * PageVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@ApiModel
public class PageVO {

    @ApiModelProperty(value = "页面id")
    private String pageId;

    @ApiModelProperty(value = "页面id 加码")
    private String pageCode;

    @ApiModelProperty(value = "页面名称")
    private String name;

    @ApiModelProperty(value = "页面状态 1:保存未提交 2:提交 3:下线")
    private int status;

    @ApiModelProperty(value = "创建人")
    private String userId;

    @ApiModelProperty(value = "间隔线样式")
    private String intervalStyle;

    @ApiModelProperty(value = "字体")
    private String font;

    @ApiModelProperty(value = "组件集合")
    private List<ModelVO> modelVOs;
}
