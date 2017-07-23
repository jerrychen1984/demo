package com.demo.example.data.service;

import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.data.po.Page;
import com.demo.example.data.service.exception.PageNameExistsException;
import com.demo.example.data.service.exception.PageNotExistsException;

import java.util.List;

/**
 * EditorService
 *
 * @author xuxiang
 * @since 17/7/15
 */
public interface EditorService {

    boolean createPage(PageRO pageRO) throws PageNameExistsException;

    boolean updatePage(PageRO pageRO) throws PageNotExistsException;

    boolean deletePage(Long pageId) throws PageNotExistsException;

    Page getPageById(Long pageId) throws PageNotExistsException;

    List<Page> listPageByUserId(Long userId, int page, int pageSize);

    int countPageByUserId(Long userId);
}
