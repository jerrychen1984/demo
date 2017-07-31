package com.demo.example.data.service;

import com.demo.example.controller.ro.editor.ModelRO;
import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.data.po.Element;
import com.demo.example.data.po.Model;
import com.demo.example.data.po.Page;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.exception.PageNameExistsException;
import com.demo.example.data.service.exception.PageNotExistsException;
import com.google.common.collect.Lists;
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
    public Long createPage(PageRO pageRO) throws PageNameExistsException {
        boolean result = false;

        Page page = repository.fetch(Page.class, Cnd.where("name", "=", pageRO.getName()));
        if (page != null) {
            throw new PageNameExistsException();
        }

        page = new Page();
        BeanUtils.copyProperties(pageRO, page);

        page = repository.insert(page);
        final Long pageId = page.getId() == null ? -1L : page.getId();

        if (pageId > 0) {
            pageRO.setPageId(String.valueOf(pageId));
            insertPageLinks(pageRO);
        }

        return pageId;
    }

    private void insertPageLinks(PageRO pageRO) {
        final Long pageId = Long.parseLong(pageRO.getPageId());
        if (!CollectionUtils.isEmpty(pageRO.getModelROs())) {
            pageRO.getModelROs().forEach(modelRO -> {
                Model model = new Model();
                BeanUtils.copyProperties(modelRO, model);

                model.setPageId(pageId);
                if (modelRO.getTitleRO() != null) {
                    model.setTitleIcon(modelRO.getTitleRO().getTitleIcon());
                    model.setTitleStatus(modelRO.getTitleRO().getTitleStatus());
                    model.setTitleStyle(modelRO.getTitleRO().getTitleStyle());
                    model.setMainTitle(modelRO.getTitleRO().getMainTitle());
                    model.setSubTitle(modelRO.getTitleRO().getSubTitle());
                    model.setMoreLink(modelRO.getTitleRO().getMoreLink());
                }

                model = repository.insert(model);
                final Long modelId = model.getId() == null ? -1L : model.getId();

                if (modelId > 0 && !CollectionUtils.isEmpty(modelRO.getElementROs())) {
                    modelRO.getElementROs().forEach(elementRO -> {
                        Element element = new Element();
                        BeanUtils.copyProperties(elementRO, element);

                        element.setPageId(pageId);
                        element.setModelId(modelId);
                        repository.insert(element);
                    });
                }
            });
        }
    }

    private Model transferModel(ModelRO modelRO, Long pageId) {
        if (modelRO != null) {
            Model model = new Model();
            BeanUtils.copyProperties(modelRO, model);

            model.setId(Long.parseLong(modelRO.getModelId()));
            model.setPageId(pageId);
            if (modelRO.getTitleRO() != null) {
                model.setTitleIcon(modelRO.getTitleRO().getTitleIcon());
                model.setTitleStatus(modelRO.getTitleRO().getTitleStatus());
                model.setTitleStyle(modelRO.getTitleRO().getTitleStyle());
                model.setMainTitle(modelRO.getTitleRO().getMainTitle());
                model.setSubTitle(modelRO.getTitleRO().getSubTitle());
                model.setMoreLink(modelRO.getTitleRO().getMoreLink());
            }
            return model;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePage(PageRO pageRO) throws PageNotExistsException {
        boolean result = false;

        Page page = repository.fetch(Page.class, Cnd.where("id", "=", pageRO.getPageId()));
        if (page == null) {
            throw new PageNotExistsException();
        }
        BeanUtils.copyProperties(pageRO, page);

        result = repository.update(page) > 0;

        final Long pageId = page.getId();
        if (result) {
            Page oldPage = getPageById(pageId);
            if (!CollectionUtils.isEmpty(oldPage.getModels())) {
                oldPage.getModels().forEach(model -> {
                    repository.delete(model);

                    if (!CollectionUtils.isEmpty(model.getElements())) {
                        model.getElements().forEach(element -> {
                            repository.delete(element);
                        });
                    }
                });
            }

            insertPageLinks(pageRO);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePage(Long pageId) throws PageNotExistsException {
        boolean result = false;

        Page page = getPageById(pageId);
        if (page == null) {
            throw new PageNotExistsException();
        }

        page.setStatus(-1);

        result = repository.update(page) > 0;

        if (result) {
            if (!CollectionUtils.isEmpty(page.getModels())) {
                page.getModels().forEach(model -> {
                    model.setStatus(-1);

                    repository.update(model);

                    if (!CollectionUtils.isEmpty(model.getElements())) {
                        model.getElements().forEach(element -> {
                            element.setStatus(-1);

                            repository.update(element);
                        });
                    }
                });
            }
        }

        return result;
    }


    @Override
    public Page getPageById(Long pageId) throws PageNotExistsException {
        Page page = repository.fetch(Page.class, Cnd.where("id", "=", pageId));
        if (page == null) {
            throw new PageNotExistsException();
        }

        fillPageLink(Lists.newArrayList(page));

        return page;
    }

    private void fillPageLink(List<Page> pages) {
        if (CollectionUtils.isEmpty(pages)) {
            return;
        }
        pages.forEach(page -> {
            Cnd modelCnd = Cnd.where("page_id", "=", page.getId());
            modelCnd.and("status", "!=", "-1");

            List<Model> models = repository.query(Model.class, modelCnd);
            if (!CollectionUtils.isEmpty(models)) {
                models.forEach(model -> {
                    Cnd elementCnd = Cnd.where("model_id", "=", model.getId());
                    elementCnd.and("status", "!=", "-1");

                    List<Element> elements = repository.query(Element.class, elementCnd);
                    model.setElements(elements);
                });

                page.setModels(models);
            }
        });
    }

    @Override
    public List<Page> listPageByUserId(Long userId, int page, int pageSize) {
        Pager pager = repository.createPager(page, pageSize);

        Cnd cnd = Cnd.where("user_id", "=", userId);
        cnd.and("status", "!=", "-1");

        List<Page> pages = repository.query(Page.class, cnd, pager);

        fillPageLink(pages);

        return pages;
    }

    @Override
    public int countPageByUserId(Long userId) {
        Cnd cnd = Cnd.where("user_id", "=", userId);
        cnd.and("status", "!=", "-1");

        return repository.count(Page.class, cnd);
    }
}
