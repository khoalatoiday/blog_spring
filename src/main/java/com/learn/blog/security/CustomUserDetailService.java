package com.learn.blog.security;

import com.learn.blog.entity.Users;
import com.learn.blog.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUserName(username).
                orElseThrow(() -> new UsernameNotFoundException("user's not found with username!"));

        // GrandtedAuthority là class spring security cung cấp hỗ trợ set quyền, ta nên sử dụng class này vì nhiều hàm của spring security hỗ trợ
        Set<GrantedAuthority> roles = user.getRoles().stream().map((role) ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        // đây là class cơ bản được spring cung cấp, ta hoàn toàn có thể custom class khác
        return new User(user.getUserName(), user.getPassword(), roles);
    }
}
