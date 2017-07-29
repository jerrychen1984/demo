package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.List;

/**
 * PageVO
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@Table("t_pages")
@TableIndexes(
        @Index(name = "idx_user", unique = false, fields = {"userId"})
)
public class Page {

    @Column("pageId")
    @Id()
    private Long id;

    /**
     * 页面名称
     */
    @Column
    @ColDefine(width = 64, notNull = true)
    private String name;

    /**
     * 页面状态 -1:被删除 1:保存未提交 2:提交
     */
    @Column
    private int status;

    /**
     * 创建人
     */
    @Column
    @ColDefine(width = 64, notNull = true)
    private String userId;

    /**
     * 间隔线样式
     */
    @Column("interval_style")
    @ColDefine(width = 64)
    private String intervalStyle;

    /**
     * 字体
     */
    @Column
    @ColDefine(width = 64)
    private String font;

    @Many(target = Model.class, field = "pageId")
    private List<Model> models;
}
