package com.epicmusic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

    private String username;
    private String email;
    private String password;
    private String role;

}