package com.sufiyandev.BlogShare.comment.controller;

import com.sufiyandev.BlogShare.comment.dto.CommentCreation;
import com.sufiyandev.BlogShare.comment.dto.CommentResponse;
import com.sufiyandev.BlogShare.comment.model.Comment;
import com.sufiyandev.BlogShare.comment.service.CommentService;
import com.sufiyandev.BlogShare.exception.BlogException;
import com.sufiyandev.BlogShare.exception.CommentException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/{slug}/comments")
    private ResponseEntity<CommentResponse> doComment(@PathVariable(name = "slug") String slug,
                                                      @RequestBody CommentCreation commentCreation) {
        Comment comment = commentService.doComment(slug, commentCreation);
        CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/{slug}/comments")
    private ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable(name = "slug") String slug) {
        List<Comment> comments = commentService.getAllComments(slug);
        List<CommentResponse> commentResponses = modelMapper.map(comments, new TypeToken<List<CommentResponse>>() {}.getType());
        return ResponseEntity.ok(commentResponses);
    }

    @RequestMapping(value = "/comments/{commentId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Deleted Successfully");
    }




    @ExceptionHandler({
            CommentService.CommentBodyException.class,
            CommentService.BlogNotFoundException.class
    })
    private ResponseEntity<CommentException> handleException(Exception ex) {
        HttpStatus status;
        String message;
        if (ex instanceof CommentService.CommentBodyException) {
            status = HttpStatus.PARTIAL_CONTENT;
            message = ex.getMessage();
        } else if (ex instanceof CommentService.BlogNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        } else {
            status = HttpStatus.BAD_REQUEST;
            message = "Something went wrong";
        }
        CommentException  commentException = CommentException.builder().message(message).build();
        return ResponseEntity.status(status).body(commentException);
    }

}
