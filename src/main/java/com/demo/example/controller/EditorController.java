package com.demo.example.controller;

import com.demo.example.controller.ro.editor.Page;
import com.demo.example.controller.vo.ResponseData;
import com.demo.example.data.service.EditorService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * EditorController
 *
 * @author xuxiang
 * @since 17/7/11
 */
@Api("编辑器")
@RestController
@RequestMapping("/editor")
public class EditorController {

    @Resource
    private EditorService editorService;

    @RequestMapping(value = "/createPage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData<?> createPage(@RequestBody Page page) {
        return ResponseData.success(true);
    }

    @RequestMapping(value = "/updatePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData<?> updatePage(@RequestBody Page page) {
        return ResponseData.success(true);
    }

    @RequestMapping(value = "/removePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData<?> removePage(@RequestParam("pageId") Long pageId) {
        return ResponseData.success(true);
    }

    @RequestMapping(value = "/getPageDetailById"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData<?> getPageDetailById(@RequestParam("pageId") Long pageId) {
        return ResponseData.success(true);
    }
}
