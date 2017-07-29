package com.demo.example.controller.vo.editor;

import com.demo.example.data.service.exception.PageNameExistsException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * PageOperateResultVO
 *
 * @author xuxiang
 * @since 17/7/23
 */
@Data
@ApiModel
@AllArgsConstructor
public class PageOperateResultVO {

    public static final int SUCCESS = 0;
    public static final int PAGE_NAME_EXISTS = 1;
    public static final int KEY_INVALID_PARAMS = 2;
    public static final int KEY_UNKNOWN_ERROR = 3;

    @ApiModelProperty(value = "返回码,0:成功, 1:页面名字已经存在, 2:参数错误, 3:未知错误"
            , dataType = "int", example = "0", required = true)
    private int code;

    @ApiModelProperty(value = "错误信息"
            , dataType = "string", example = "成功")
    private String message = "";

    private Long pageId;

    public static PageOperateResultVO success(Long pageId) {
        return new PageOperateResultVO(SUCCESS, "创建成功", pageId);
    }

    public static PageOperateResultVO error(Exception e) {
        if (e instanceof PageNameExistsException) {
            return new PageOperateResultVO(PAGE_NAME_EXISTS, e.getMessage(), -1L);
        } else {
            return new PageOperateResultVO(KEY_UNKNOWN_ERROR, e.getMessage(), -1L);
        }
    }
}
