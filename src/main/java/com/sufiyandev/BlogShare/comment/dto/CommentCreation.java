package com.sufiyandev.BlogShare.comment.dto;

import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class CommentCreation {
    private String body;
}
