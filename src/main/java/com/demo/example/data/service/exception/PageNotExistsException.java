package com.demo.example.data.service.exception;

/**
 * PageNotExistsException
 *
 * @author xuxiang
 * @since 17/7/22
 */
public class PageNotExistsException extends Exception{
    public PageNotExistsException() {
        super("当前页面不存在!");
    }
}
