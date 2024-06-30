package com.learn.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    private String name;
}
