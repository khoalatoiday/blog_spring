package com.learn.blog.controller;

import com.learn.blog.entity.Users;
import com.learn.blog.payload.JwtAuthResponse;
import com.learn.blog.payload.LoginDto;
import com.learn.blog.payload.RegisterDto;
import com.learn.blog.security.JwtTokenProvider;
import com.learn.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/register"})
    public ResponseEntity<Users> register(@RequestBody RegisterDto registerDto){
        Users user = authService.register(registerDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
