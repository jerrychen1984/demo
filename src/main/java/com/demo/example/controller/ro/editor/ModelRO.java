package com.demo.example.controller.ro.editor;

import lombok.Data;

import java.util.List;

/**
 * ModelVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
public class ModelRO {

    /**
     * 组件名称
     */
    private String name;

    /**
     * 组件类型
     * @see com.demo.example.controller.enums.ModelType
     */
    private int type;

    /**
     * 标题样式
     */
    private String titleStyle;

    /**
     * 主标题
     */
    private String mainTitle;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 正文文本
     */
    private String text;

    /**
     * 状态 1:上线 2:隐藏
     */
    private int status;

    /**
     * 元素集合
     */
    private List<ElementRO> elementROs;

    /**
     * 排序字段
     */
    private int order;
}
