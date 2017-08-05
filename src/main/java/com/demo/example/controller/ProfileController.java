package com.demo.example.controller;

import com.demo.example.controller.ro.profile.MyPageListRO;
import com.demo.example.controller.vo.profile.MyPageListVO;
import com.demo.example.data.po.Page;
import com.demo.example.data.po.User;
import com.demo.example.data.repository.Repository;
import com.demo.example.data.service.EditorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(value = "我的相关", tags = "我的相关", description = "我的页面")
@RestController
@RequestMapping("/editor")
public class ProfileController {

    @Autowired
    private Repository repository;
    @Autowired
    private EditorService editorService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/pages"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "我的H5页面列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "我的H5页面", response = MyPageListVO.class)
    })
    public ResponseEntity<?> myPages(@RequestBody MyPageListRO paging) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // email
        String username = (String) authentication.getPrincipal();
        User user = repository.fetch(User.class, Cnd.where("username", "=", username));


        final Pager pager = new Pager(paging.getPage(), paging.getPageSize());
        List<Page> pages = repository.query(Page.class, Cnd.where("user_id", "=", user.getId()), pager);

        MyPageListVO result = new MyPageListVO();
        result.setCount(editorService.countPageByUserId(user.getId()));
        result.setPage(pager.getPageNumber());
        result.setPageSize(pager.getPageSize());
        result.setItems(pages.stream().map(e -> {
            MyPageListVO.PageVO pageVO = new MyPageListVO.PageVO();
            BeanUtils.copyProperties(e, pageVO);
            return pageVO;
        }).collect(toList()));

        return ResponseEntity.ok(result);

    }

}
