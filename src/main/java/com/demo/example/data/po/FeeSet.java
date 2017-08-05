package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;

@Data
@Table("t_feesets")
@TableIndexes(
        @Index(name = "uk_1", fields = "code")
)
public class FeeSet {

    @Id
    private Long id;

    @Column
    @ColDefine(notNull = true)
    private Integer code;

    @Column
    @ColDefine(width = 255, notNull = true)
    private String title;

    @Column
    @ColDefine(width = 255, notNull = true)
    private String description;

    @Column("max_pages")
    @ColDefine(notNull = true)
    private Long maxPages;

    @Column("max_page_view")
    @ColDefine(notNull = true)
    private Long maxPageView;

    @Column()
    @ColDefine(notNull = true)
    private Integer validity;

    @Column()
    @ColDefine(notNull = true)
    private Double price;

}
