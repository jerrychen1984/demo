package com.demo.example.controller;

import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.controller.vo.editor.*;
import com.demo.example.data.po.Page;
import com.demo.example.data.service.EditorService;
import com.demo.example.data.service.exception.PageNameExistsException;
import com.demo.example.data.service.exception.PageNotExistsException;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * EditorController
 *
 * @author xuxiang
 * @since 17/7/11
 */
@Api(value = "编辑器", tags = "编辑器", description = "编辑器服务")
@RestController
@RequestMapping("/editor")
public class EditorController {

    @Resource
    private EditorService editorService;

    @RequestMapping(value = "/createPage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PageOperateResultVO createPage(@RequestBody PageRO pageRO) {
        Long pageId = -1L;
        try {
            pageId = editorService.createPage(pageRO);
        } catch (PageNameExistsException e) {
            return PageOperateResultVO.error(e);
        }
        return PageOperateResultVO.success(pageId);
    }

    @RequestMapping(value = "/updatePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PageOperateResultVO updatePage(@RequestBody PageRO pageRO) {
        try {
            boolean result = editorService.updatePage(pageRO);
            if (result) {
                return PageOperateResultVO.success(Long.parseLong(pageRO.getPageId()));
            }
        } catch (PageNotExistsException e) {
            return PageOperateResultVO.error(e);
        } catch (Exception e) {
            return PageOperateResultVO.error(e);
        }
        return PageOperateResultVO.error(null);
    }

    @RequestMapping(value = "/removePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PageOperateResultVO removePage(@RequestParam("pageId") Long pageId) {
        try {
            editorService.deletePage(pageId);
        } catch (PageNotExistsException e) {
            return PageOperateResultVO.error(e);
        }

        return PageOperateResultVO.success(pageId);
    }

    @RequestMapping(value = "/getPageDetailById"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<?> getPageDetailById(@RequestParam("pageId") Long pageId) {
        try {
            Page page = editorService.getPageById(pageId);

            List<PageVO> pageVOs = transferPageVO(Lists.newArrayList(page));
            if (CollectionUtils.isEmpty(pageVOs)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pageVOs.get(0));
        } catch (PageNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<PageVO> transferPageVO(List<Page> pages) {
        List<PageVO> pageVOs = new ArrayList<>();

        if (!CollectionUtils.isEmpty(pages)) {
            pages.forEach(page -> {
                PageVO pageVO = new PageVO();

                BeanUtils.copyProperties(page, pageVO);
                pageVO.setPageId(String.valueOf(page.getId()));

                if (!CollectionUtils.isEmpty(page.getModels())) {
                    List<ModelVO> modelVOs = new ArrayList<>();

                    page.getModels().forEach(model -> {
                        ModelVO modelVO = new ModelVO();
                        BeanUtils.copyProperties(model, modelVO);

                        modelVO.setModelId(String.valueOf(model.getId()));

                        ModelTitleVO modelTitleVO = new ModelTitleVO();
                        BeanUtils.copyProperties(model, modelTitleVO);
                        modelVO.setModelTitleVO(modelTitleVO);

                        if (!CollectionUtils.isEmpty(model.getElements())) {
                            List<ElementVO> elementVOs = new ArrayList<>();

                            model.getElements().forEach(element -> {
                                ElementVO elementVO = new ElementVO();
                                BeanUtils.copyProperties(element, elementVO);

                                elementVO.setElementId(String.valueOf(element.getId()));

                                elementVOs.add(elementVO);
                            });

                            modelVO.setElementVOs(elementVOs);
                        }

                        modelVOs.add(modelVO);
                    });

                    pageVO.setModelVOs(modelVOs);
                }

                pageVOs.add(pageVO);
            });
        }

        return pageVOs;
    }

    @RequestMapping(value = "/listPagesByUserId"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<?> listPagesByUserId(@RequestParam("userId") Long userId,
                                               @RequestParam("page") int page,
                                               @RequestParam("pageSize") int pageSize) {
        try {
            PageListResultVO resultVO = new PageListResultVO();

            List<Page> pages = editorService.listPageByUserId(userId, page, pageSize);

            List<PageVO> pageVOs = transferPageVO(pages);

            resultVO.setPages(pageVOs);
            resultVO.getPagingVO().setCount(editorService.countPageByUserId(userId));
            resultVO.getPagingVO().setPage(page);
            resultVO.getPagingVO().setPageSize(pageSize);

            return ResponseEntity.ok(resultVO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
