package com.demo.example.controller.vo.editor;

import lombok.Data;

/**
 * Element
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
public class Element {

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 图片高
     */
    private int height;

    /**
     * 图片宽
     */
    private int width;

    /**
     * 标题
     */
    private String title;

    /**
     * 跳转地址
     */
    private String jumpUrl;

    /**
     * 价格
     */
    private long price;

    /**
     * 价格文案
     */
    private String priceText;

    /**
     * 定位
     */
    private String position;
}
