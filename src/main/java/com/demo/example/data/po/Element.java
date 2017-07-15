package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

/**
 * Element
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
public class Element {

    @Id
    private Long id;

    @Column("model_id")
    private Long modelId;

    /**
     * 图片地址
     */
    @Column
    @ColDefine(width = 200, notNull = true)
    private String pic;

    /**
     * 图片高
     */
    @Column
    private int height;

    /**
     * 图片宽
     */
    @Column
    private int width;

    /**
     * 标题
     */
    @Column
    @ColDefine(width = 1000, notNull = true)
    private String title;

    /**
     * 跳转地址
     */
    @Column("jump_url")
    @ColDefine(width = 200, notNull = true)
    private String jumpUrl;

    /**
     * 价格
     */
    @Column
    private long price;

    /**
     * 价格文案
     */
    @Column("price_text")
    @ColDefine(width = 200, notNull = true)
    private String priceText;

    /**
     * 定位
     */
    @Column
    @ColDefine(width = 200, notNull = true)
    private String position;
}