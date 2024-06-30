package com.learn.blog.service;

import com.learn.blog.entity.Role;
import com.learn.blog.entity.Users;
import com.learn.blog.exception.BlogApiException;
import com.learn.blog.exception.ResourceNotFoundException;
import com.learn.blog.payload.LoginDto;
import com.learn.blog.payload.RegisterDto;
import com.learn.blog.repository.RoleRepository;
import com.learn.blog.repository.UsersRepository;
import com.learn.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserName(), loginDto.getPassword()
        ));

        if(!authentication.isAuthenticated()) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "User login failed!");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public Users register(RegisterDto registerDto) {

        if(usersRepository.existsByUserName(registerDto.getUserName())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "UserName is already exists!");
        }

        if(usersRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }

        Users user = new Users();
        user.setEmail(registerDto.getEmail());
        user.setUserName(registerDto.getUserName());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new BlogApiException(HttpStatus.NOT_FOUND, "ROLE IS NOT FOUND!")
        );
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleUser);
        user.setRoles(roles);

        usersRepository.save(user);
        return user;
    }
}
