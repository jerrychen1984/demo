package com.demo.example.controller;

import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.controller.vo.editor.PageListResultVO;
import com.demo.example.controller.vo.editor.PageOperateResultVO;
import com.demo.example.controller.vo.editor.PageVO;
import com.demo.example.data.po.Page;
import com.demo.example.data.service.EditorService;
import com.demo.example.data.service.exception.PageNameExistsException;
import com.demo.example.data.service.exception.PageNotExistsException;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
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
        return PageOperateResultVO.success(Long.parseLong(pageRO.getPageId()));
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
        PageVO pageVO = new PageVO();
        try {
            Page page = editorService.getPageById(pageId);

            return ResponseEntity.ok(pageVO);
        } catch (PageNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

            List<PageVO> pageVOs = new ArrayList<>();

            List<Page> pages = editorService.listPageByUserId(userId, page, pageSize);

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
