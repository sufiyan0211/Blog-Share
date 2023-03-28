package com.sufiyandev.BlogShare.blog.dto;

import com.sufiyandev.BlogShare.user.dto.UserCreation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateBlog {
    private String title;
    private String body;
    private String slug;
}
