package com.sufiyandev.BlogShare.blog.repository;

import com.sufiyandev.BlogShare.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    public Optional<Blog> findBlogBySlug(String slug);
}
