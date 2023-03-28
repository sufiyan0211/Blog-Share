package com.sufiyandev.BlogShare.blog.dto;

import com.sufiyandev.BlogShare.user.dto.UserCreation;
import com.sufiyandev.BlogShare.user.dto.UserResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BlogResponse {
    private Long id;
    private String title;
    private String body;
    private Long likes;
    private String slug;
    private LocalDate createdAt;
    private UserResponse createdBy;
    private LocalDate modifiedAt;
    private UserResponse modifiedBy;
}
