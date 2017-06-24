package com.demo.example.controller;

import com.demo.example.data.service.AuthService;
import com.demo.example.security.jwt.JwtAuthenticationRequest;
import com.demo.example.security.jwt.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    // Http request header
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "${jwt.route.authentication.path}"
            , method = RequestMethod.POST
            , produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<?> createToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException{
        final String token = authService.login(authenticationRequest.getUsername()
                , authenticationRequest.getPassword());

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}"
            , method = RequestMethod.GET
            , produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request) throws AuthenticationException{
        String bearer = request.getHeader(AUTHORIZATION);
        String refreshedToken = authService.refresh(bearer);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

}

