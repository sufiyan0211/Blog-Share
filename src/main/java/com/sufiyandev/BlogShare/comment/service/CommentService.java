package com.sufiyandev.BlogShare.comment.service;

import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.blog.service.BlogService;
import com.sufiyandev.BlogShare.comment.dto.CommentCreation;
import com.sufiyandev.BlogShare.comment.model.Comment;
import com.sufiyandev.BlogShare.comment.repository.CommentRepository;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // TODO: Remove UserRepository dependency from here. It was temporirily added for creating & fetching user
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogService blogService;


    public Comment doComment(String slug, CommentCreation commentCreation) {
        Comment comment = new Comment();
        comment.setBlog(blogService.getBlogBySlug(slug));

        if (commentCreation.getBody() == null) throw new CommentBodyException();
        comment.setBody(commentCreation.getBody());
        comment.setCreatedAt(LocalDate.now());

        // TODO: Add user to blog (set createdBy)
        User user = new User();
        user.setUsername("sufi");
        user.setPassword("1235");
        userRepository.save(user);
        comment.setCreatedBy(user);
        // Remove till here

        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getAllComments(String slug) {
        Blog blog = blogService.getBlogBySlug(slug);
        List<Comment> comments = commentRepository.findAllByBlog(blog).orElseThrow(() -> new BlogNotFoundException(slug));
        return comments;
    }


    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    public static class CommentBodyException extends IllegalArgumentException {
        public CommentBodyException() {
            super("Comment Body Can not be empty");
        }
    }

    public static class BlogNotFoundException extends IllegalArgumentException {
        public BlogNotFoundException(String slug) {
            super("Blog of slug " + slug + " not found.");
        }
    }
}
