package com.demo.example.data.service;

import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.data.po.Element;
import com.demo.example.data.po.Model;
import com.demo.example.data.po.Page;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.exception.PageNameExistsException;
import com.demo.example.data.service.exception.PageNotExistsException;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * EditorServiceImpl
 *
 * @author xuxiang
 * @since 17/7/15
 */
@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private Repository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createPage(PageRO pageRO) throws PageNameExistsException {
        boolean result = false;

        Page page = repository.fetch(Page.class, Cnd.where("name", "=", pageRO.getName()));
        if (page != null) {
            throw new PageNameExistsException();
        }

        page = new Page();
        BeanUtils.copyProperties(pageRO, page);

        page = repository.insert(page);
        result = page.getId() != null && page.getId() > 0;

        if (result && !CollectionUtils.isEmpty(pageRO.getModelROs())) {
            pageRO.getModelROs().forEach(modelRO -> {
                Model model = new Model();
                BeanUtils.copyProperties(modelRO, model);

                if (modelRO.getTitleRO() != null) {
                    model.setTitleIcon(modelRO.getTitleRO().getTitleIcon());
                    model.setTitleStatus(modelRO.getTitleRO().getTitleStatus());
                }

                repository.insert(model);

                if (CollectionUtils.isEmpty(modelRO.getElementROs())) {
                    modelRO.getElementROs().forEach(elementRO -> {
                        Element element = new Element();
                        BeanUtils.copyProperties(elementRO, element);

                        repository.insert(element);
                    });
                }
            });
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePage(PageRO pageRO) throws PageNotExistsException {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePage(Long pageId) throws PageNotExistsException {
        boolean result = false;

        Page page = repository.fetch(Page.class, Cnd.where("id", "=", pageId));
        if (page == null) {
            throw new PageNotExistsException();
        }

        page.setStatus(-1);

        result = repository.update(page) > 0;

        return result;
    }

    @Override
    public Page getPageById(Long pageId) throws PageNotExistsException {
        Page page = repository.fetch(Page.class, Cnd.where("id", "=", pageId));
        if (page == null) {
            throw new PageNotExistsException();
        }

        return page;
    }

    @Override
    public List<Page> listPageByUserId(Long userId, int page, int pageSize) {
        Pager pager = repository.createPager(page, pageSize);

        List<Page> pages = repository.query(Page.class, Cnd.where("user_id", "=", userId), pager);

        return pages;
    }

    @Override
    public int countPageByUserId(Long userId) {
        return repository.count(Page.class, Cnd.where("user_id", "=", userId));
    }
}
