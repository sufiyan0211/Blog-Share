package com.sufiyandev.BlogShare.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String bio;
    private String token;
}
