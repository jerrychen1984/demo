package com.demo.example.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PagingVO
 *
 * @author xuxiang
 * @since 17/7/23
 */
@Data
@NoArgsConstructor
public class PagingVO {

    @ApiModelProperty(value = "当前页码,从1开始")
    private int page = 1;

    @ApiModelProperty(value = "每页数量")
    private int pageSize = 20;

    @ApiModelProperty(value = "总数量")
    private int count;

    public PagingVO(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
