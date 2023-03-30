package com.sufiyandev.BlogShare.comment.service;

import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.blog.service.BlogService;
import com.sufiyandev.BlogShare.comment.dto.CommentCreation;
import com.sufiyandev.BlogShare.comment.dto.CommentUpdate;
import com.sufiyandev.BlogShare.comment.model.Comment;
import com.sufiyandev.BlogShare.comment.repository.CommentRepository;
import com.sufiyandev.BlogShare.security.JWTService;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
import com.sufiyandev.BlogShare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    private JWTService jwtService;


    public Comment doComment(String slug, CommentCreation commentCreation, String accessToken) {
        Comment comment = new Comment();
        comment.setBlog(blogService.getBlogBySlug(slug));

        if (commentCreation.getBody() == null) throw new CommentBodyException();
        comment.setBody(commentCreation.getBody());
        comment.setCreatedAt(LocalDate.now());

        Long userId = jwtService.retrieveUserId(accessToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserService.UserNotFoundException(userId));
        comment.setCreatedBy(user);

        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getAllComments(String slug) {
        Blog blog = blogService.getBlogBySlug(slug);
        List<Comment> comments = commentRepository.findAllByBlog(blog).orElseThrow(() -> new BlogNotFoundException(slug));
        return comments;
    }


    public void deleteComment(Long commentId, String accessToken) {
        Long userId = jwtService.retrieveUserId(accessToken);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        Long commentsUserId = comment.getCreatedBy().getId();
        Long blogsUserId = comment.getBlog().getCreatedBy().getId();
        if(userId != commentsUserId && userId != blogsUserId) throw new UserService.InvalidUserException(
                comment.getCreatedBy().getId());
        commentRepository.deleteById(commentId);
    }


    public Comment editComment(Long commentId, CommentUpdate commentUpdate, String accessToken) {
        Long userId = jwtService.retrieveUserId(accessToken);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if(userId != comment.getCreatedBy().getId()) throw new UserService.InvalidUserException(
                comment.getCreatedBy().getId());
        comment.setBody(commentUpdate.getBody());
        comment.setModifiedAt(LocalDate.now());
        comment.setModifiedBy(comment.getCreatedBy()); // because only owner of comment can edit its comment
        return commentRepository.save(comment);
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

    public static class CommentNotFoundException extends IllegalArgumentException {
        public CommentNotFoundException(Long commentId) {
            super(String.format("Comment with comment id: %s not found.", commentId));
        }
    }
}
