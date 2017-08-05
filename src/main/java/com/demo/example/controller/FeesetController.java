package com.demo.example.controller;

import com.demo.example.controller.vo.feeset.FeesetListVO;
import com.demo.example.controller.vo.feeset.MyCurrentFeesetVO;
import com.demo.example.data.po.FeeSet;
import com.demo.example.data.po.User;
import com.demo.example.data.po.UserFeeSet;
import com.demo.example.data.repository.Repository;
import com.demo.example.manager.FeesetManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.nutz.dao.Cnd;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(value = "套餐", tags = "套餐", description = "套餐相关服务")
@RestController
@RequestMapping("/feeset")
public class FeesetController {

    @Autowired
    private Repository repository;

    @Autowired
    private FeesetManager feesetManager;


    @RequestMapping(value = "/list"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "套餐列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "套餐列表", response = FeesetListVO.class)
    })
    public ResponseEntity<?> getFeesetList() {

        List<FeeSet> feeSets = repository.query(FeeSet.class, null);

        final FeesetListVO result = new FeesetListVO();
        result.getFeesets().addAll(feeSets.stream()
                .filter(e -> e.getCode() != 0)
                .map(e -> {
                    FeesetListVO.FeesetItemVO item = new FeesetListVO.FeesetItemVO();
                    BeanUtils.copyProperties(e, item);
                    return item;
                })
                .collect(toList()));

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/current"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "我的当前套餐")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "我的当前套餐", response = MyCurrentFeesetVO.class)
    })
    public ResponseEntity<?> myCurrentFeeset() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // email
        String username = (String) authentication.getPrincipal();
        final User user = repository.fetch(User.class, Cnd.where("username", "=", username));
        final FeeSet curr = feesetManager.getUserCurrFeeset(user.getId());

        MyCurrentFeesetVO result = new MyCurrentFeesetVO();
        MyCurrentFeesetVO.FeesetVO feesetVO = new MyCurrentFeesetVO.FeesetVO();
        BeanUtils.copyProperties(curr, feesetVO);
        result.setFeeset(feesetVO);

        List<UserFeeSet> allFeeSets = feesetManager.getUserAllFeeset(user.getId());
        result.setCurrPageView((int)allFeeSets.stream().mapToLong(UserFeeSet::getCurrPageView).sum());
        result.setMaxPageView((int)allFeeSets.stream().mapToLong(UserFeeSet::getMaxPageView).sum());

        return ResponseEntity.ok(result);
    }
}
