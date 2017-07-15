package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.List;

/**
 * Page
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Data
@Table("t_page")
public class Page {
    @Id
    private Long id;

    /**
     * 页面名称
     */
    @Column
    @ColDefine(width = 64, notNull = true)
    private String name;

    /**
     * 页面状态 1:保存未提交 2:提交
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
}
