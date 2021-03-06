package com.demo.example.controller.vo.editor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * ModelVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@ApiModel
public class ModelVO {

    @ApiModelProperty(value = "组件id")
    private String modelId;

    @ApiModelProperty(value = "组件名称")
    private String name;

    /**
     *
     * @see com.demo.example.controller.enums.ModelType
     */
    @ApiModelProperty(value = "组件类型")
    private int type;

    @ApiModelProperty(value = "组件标题")
    private ModelTitleVO modelTitleVO;

    @ApiModelProperty(value = "正文文本")
    private String text;

    @ApiModelProperty(value = "状态 1:上线 2:隐藏")
    private int status;

    @ApiModelProperty(value = "元素集合")
    private List<ElementVO> elementVOs;

    @ApiModelProperty(value = "排序字段")
    private int order;
}
