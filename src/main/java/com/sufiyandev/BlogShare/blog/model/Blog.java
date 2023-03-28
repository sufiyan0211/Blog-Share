package com.sufiyandev.BlogShare.blog.model;

import com.sufiyandev.BlogShare.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "likes", nullable = false)
    private Long likes; // TODO initialize it with zero when blog object is created

    @Column(name = "slug", nullable = false)
    private String slug;

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
