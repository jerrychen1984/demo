package com.demo.example.controller.vo.editor;

import com.demo.example.controller.vo.PagingVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * PageListResultVO
 *
 * @author xuxiang
 * @since 17/7/23
 */
@Data
@ApiModel
public class PageListResultVO {

    @ApiModelProperty(value = "页面列表")
    private List<PageVO> pages;

    @ApiModelProperty(value = "分页对象")
    private PagingVO pagingVO = new PagingVO();
}
