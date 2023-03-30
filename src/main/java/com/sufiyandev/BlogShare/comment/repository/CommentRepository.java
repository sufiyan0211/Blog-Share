package com.sufiyandev.BlogShare.comment.repository;

import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Optional<List<Comment>> findAllByBlog(Blog blog);
}
