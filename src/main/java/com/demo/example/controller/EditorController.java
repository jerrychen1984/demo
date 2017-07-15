package com.demo.example.controller;

import com.demo.example.controller.ro.editor.PageRO;
import com.demo.example.controller.vo.editor.PageVO;
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
    public Boolean createPage(@RequestBody PageRO pageRO) {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/updatePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Boolean updatePage(@RequestBody PageRO pageRO) {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/removePage"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Boolean removePage(@RequestParam("pageId") Long pageId) {
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/getPageDetailById"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PageVO getPageDetailById(@RequestParam("pageId") Long pageId) {
        return null;
    }
}
