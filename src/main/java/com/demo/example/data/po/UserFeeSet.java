package com.demo.example.data.po;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.util.Date;

@Data
@Table("t_user_feesets")
@TableIndexes(
        value = {
                @Index(name = "uk_1", fields = {"userId", "feeSetCode"}),
                @Index(unique = false, name = "idx_1", fields = {"userId", "feeSetCode", "status"})
        }
)
public class UserFeeSet {
    @Id
    private Long id;

    @Column("gmt_create")
    @ColDefine(notNull = true)
    private Date gmtCreate;

    @Column("gmt_modified")
    @ColDefine(notNull = true)
    private Date gmtModified;

    @Column("user_id")
    @ColDefine(notNull = true)
    private Long userId;

    @Column("fee_set_code")
    @ColDefine(notNull = true)
    private Integer feeSetCode;

    // 支付状态 0：待支付,1: 已支付, 2:支付失败
    @Column
    @ColDefine(notNull = true)
    private Integer status = 0;

    @Column("gmt_start")
    private Date gmtStart;

    @Column("gmt_end")
    private Date gmtEnd;

    @Column("curr_page_view")
    @ColDefine(notNull = true)
    private Long currPageView = 0L;

    @Column("max_page_view")
    @ColDefine(notNull = true)
    private Long maxPageView = 0L;
}

