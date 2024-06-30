package com.learn.blog.service;

import com.learn.blog.entity.Users;
import com.learn.blog.payload.LoginDto;
import com.learn.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    Users register(RegisterDto registerDto);
}
