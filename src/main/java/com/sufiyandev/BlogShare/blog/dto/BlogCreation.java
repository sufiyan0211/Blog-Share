package com.sufiyandev.BlogShare.blog.dto;

import com.sufiyandev.BlogShare.user.dto.UserCreation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BlogCreation {
    private String title;
    private String body;
    private Long likes;
    private String slug;
    private LocalDate createdAt;
    private UserCreation createdBy;
    private LocalDate modifiedAt;
    private UserCreation modifiedBy;
}
