package com.sufiyandev.BlogShare.comment.controller;

import com.sufiyandev.BlogShare.comment.dto.CommentCreation;
import com.sufiyandev.BlogShare.comment.dto.CommentResponse;
import com.sufiyandev.BlogShare.comment.dto.CommentUpdate;
import com.sufiyandev.BlogShare.comment.model.Comment;
import com.sufiyandev.BlogShare.comment.service.CommentService;
import com.sufiyandev.BlogShare.exception.BlogException;
import com.sufiyandev.BlogShare.exception.CommentException;
import com.sufiyandev.BlogShare.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Post a comment. <br>
     * <b>Authentication</b> is required for this api. <br>
     *
     * @param slug
     * @param commentCreation
     * @param authorization
     * @return
     */
    @PostMapping("/{slug}/comments")
    private ResponseEntity<CommentResponse> doComment(@PathVariable(name = "slug") String slug,
                                                      @RequestBody CommentCreation commentCreation,
                                                      @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.substring("Bearer ".length());
        Comment comment = commentService.doComment(slug, commentCreation, accessToken);
        CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
        return ResponseEntity.ok(commentResponse);
    }

    /**
     * Get all the comments of a blog(whose slug is {slug}). <br>
     * There is <b>no authentication</b> required for this API. <br>
     *
     * @param slug
     * @return
     */
    @GetMapping("/{slug}/comments")
    private ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable(name = "slug") String slug) {
        List<Comment> comments = commentService.getAllComments(slug);
        List<CommentResponse> commentResponses = modelMapper.map(comments, new TypeToken<List<CommentResponse>>() {}.getType());
        return ResponseEntity.ok(commentResponses);
    }


    /**
     * Owner of the comment or the owner of the blog can delete the comment. <br>
     * <b>Authentication</b> is required for this api. <br>
     * @param commentId
     * @param authorization
     * @return
     */
    @RequestMapping(value = "{slug}/comments/{commentId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                 @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.substring("Bearer ".length());
        commentService.deleteComment(commentId, accessToken);
        return ResponseEntity.ok("Deleted Successfully");
    }

    /**
     * This API is for editing the comment. <br>
     * Only owner of the comment can edit its comments. <br>
     * <b>Authentication</b> is required for this api. <br>
     *
     * @param commentId
     * @param commentUpdate
     * @param authorization
     * @return
     */
    @PutMapping("/comments/{commentId}")
    private ResponseEntity<CommentResponse> editComment(@PathVariable Long commentId,
                                                 @RequestBody CommentUpdate commentUpdate,
                                                 @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.substring("Bearer ".length());
        Comment comment = commentService.editComment(commentId, commentUpdate, accessToken);
        CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
        return ResponseEntity.ok(commentResponse);
    }



    /**
     * This is used to handle comment related exceptions. <br>
     * @param ex
     * @return
     */
    @ExceptionHandler({
            CommentService.CommentBodyException.class,
            CommentService.BlogNotFoundException.class,
            CommentService.CommentNotFoundException.class,
            UserService.InvalidUserException.class
    })
    private ResponseEntity<CommentException> handleException(Exception ex) {
        HttpStatus status;
        String message;
        if (ex instanceof CommentService.CommentBodyException) {
            status = HttpStatus.PARTIAL_CONTENT;
            message = ex.getMessage();
        } else if (ex instanceof CommentService.BlogNotFoundException ||
                ex instanceof CommentService.CommentNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        } else if (ex instanceof UserService.InvalidUserException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            status = HttpStatus.BAD_REQUEST;
            message = "Something went wrong";
        }
        CommentException  commentException = CommentException.builder().message(message).build();
        return ResponseEntity.status(status).body(commentException);
    }

}
