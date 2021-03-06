package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * ElementVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@Table("t_elements")
public class Element {

    @Id
    private Long id;

    @Column("model_id")
    private Long modelId;

    @Column("page_id")
    private Long pageId;

    /**
     * 图片地址
     */
    @Column
    @ColDefine(width = 255)
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
    @ColDefine(width = 512)
    private String title;

    /**
     * 副标题
     */
    @Column("sub_title")
    @ColDefine(width = 512)
    private String subTitle;

    /**
     * 跳转地址
     */
    @Column("jump_url")
    @ColDefine(width = 255)
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
    @ColDefine(width = 255)
    private String priceText;

    /**
     * 定位
     */
    @Column
    @ColDefine(width = 255)
    private String position;

    /**
     * 状态 -1:被删除 1:上线 2:隐藏
     */
    @Column
    private int status;

    /**
     * 排序字段
     */
    @Column("show_order")
    private Integer showOrder = 0;
}
