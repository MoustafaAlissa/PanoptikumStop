package com.example.panoptikumstop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

