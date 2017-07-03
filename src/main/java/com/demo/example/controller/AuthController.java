package com.demo.example.controller;

import com.demo.example.controller.vo.ResponseData;
import com.demo.example.data.service.AuthService;
import com.demo.example.data.service.exception.EmailWasNotVerifiedException;
import com.demo.example.security.jwt.JwtAuthenticationRequest;
import com.demo.example.security.jwt.JwtAuthenticationResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api("访问令牌API")
@RestController
public class AuthController {
    // Http request header
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "${jwt.route.authentication.path}"
            , method = RequestMethod.POST
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "换取访问令牌")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "用户名或密码错误")
    })
    public ResponseData<?> createToken(@ApiParam @RequestBody JwtAuthenticationRequest authenticationRequest) {

        try {
            final String token = authService.login(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword());
            return ResponseData.success(new JwtAuthenticationResponse(token));

        } catch (EmailWasNotVerifiedException e) {
            return ResponseData.error(e.getMessage());
        }

    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}"
            , method = RequestMethod.GET
            , produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @ApiOperation(value = "刷新访问令牌")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "token无效")
    })
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request) throws AuthenticationException {
        String bearer = request.getHeader(AUTHORIZATION);
        String refreshedToken = authService.refresh(bearer);
        if (refreshedToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

}

