package com.sufiyandev.BlogShare.comment.dto;

import com.sufiyandev.BlogShare.blog.dto.BlogResponse;
import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.user.dto.UserResponse;
import com.sufiyandev.BlogShare.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class CommentResponse {

    private Long id;

    private BlogResponse blog;

    private String body;

    private LocalDate createdAt;

    private UserResponse createdBy;

    private LocalDate modifiedAt;

    private UserResponse modifiedBy;
}
