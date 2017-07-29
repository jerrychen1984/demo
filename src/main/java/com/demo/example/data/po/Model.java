package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

/**
 * ModelVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@Table("t_models")
public class Model {

    @Id
    private Long id;

    @Column("page_id")
    private Long pageId;

    /**
     * 组件名称
     */
    @Column
    @ColDefine(width = 64, notNull = true)
    private String name;

    /**
     * 组件类型
     *
     * @see com.demo.example.controller.enums.ModelType
     */
    @Column
    private int type;

    /**
     * 标题样式
     */
    @Column("title_style")
    @ColDefine(width = 64, notNull = true)
    private String titleStyle;

    /**
     * 主标题
     */
    @Column("main_title")
    @ColDefine(width = 512, notNull = true)
    private String mainTitle;

    /**
     * 副标题
     */
    @Column("sub_title")
    @ColDefine(width = 1000)
    private String subTitle;

    /**
     * 正文文本
     */
    @Column
    @ColDefine(width = 1000, notNull = true)
    private String text;

    @Column("title_status")
    @ColDefine(width = 255, notNull = true)
    private String titleStatus;

    @Column("title_icon")
    @ColDefine(width = 255, notNull = true)
    private String titleIcon;

    /**
     * 状态 -1:被删除 1:上线 2:隐藏
     */
    @Column
    private Integer status = 0;

    /**
     * 排序字段
     */
    @Column("show_order")
    private Integer showOrder = 0;

    @Many(target = Element.class, field = "modelId")
    private List<Element> elements;
}
