package com.demo.example.controller.ro.editor;

import lombok.Data;

import java.util.List;

/**
 * PageVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
public class PageRO {

    /**
     * 页面名称
     */
    private String name;

    /**
     * 页面状态 1:保存未提交 2:提交 3:下线
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
    private List<ModelRO> modelROs;
}
