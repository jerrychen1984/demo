package com.demo.example.data.service;

import com.demo.example.controller.ro.editor.PageRO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EditorServiceImpl
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Service
public class EditorServiceImpl implements EditorService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createPage(PageRO pageRO) {

        return false;
    }
}
