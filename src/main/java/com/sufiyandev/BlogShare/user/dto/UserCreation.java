package com.sufiyandev.BlogShare.user.dto;

import lombok.Data;

@Data
public class UserCreation {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String bio;
}
