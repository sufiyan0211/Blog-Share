package com.sufiyandev.BlogShare.comment.model;

import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BlogId")
    private Blog blog;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "createdByUserId", nullable = false)
    private User createdBy;

    @Column(name = "modifiedAt")
    private LocalDate modifiedAt;

    @ManyToOne
    @JoinColumn(name = "modifiedByUserId")
    private User modifiedBy;
}
