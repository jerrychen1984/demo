package com.demo.example.controller.vo.editor;

import lombok.Data;

import java.util.List;

/**
 * Page
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
public class Page {

    /**
     * 页面名称
     */
    private String name;

    /**
     * 页面状态 1:保存未提交 2:提交
     */
    private int status;

    /**
     * 创建人
     */
    private String userId;

    /**
     * 间隔线样式
     */
    private String intervalStyle;

    /**
     * 字体
     */
    private String font;

    /**
     * 组件集合
     */
    private List<Model> models;
}
