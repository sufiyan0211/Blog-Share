package com.sufiyandev.BlogShare.blog.controller;

import com.sufiyandev.BlogShare.blog.dto.BlogCreation;
import com.sufiyandev.BlogShare.blog.dto.BlogResponse;
import com.sufiyandev.BlogShare.blog.dto.UpdateBlog;
import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.blog.service.BlogService;
import com.sufiyandev.BlogShare.exception.BlogException;
import com.sufiyandev.BlogShare.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    private ResponseEntity<BlogResponse> createBlog(@RequestBody BlogCreation blogCreation,
                                                    @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.substring("Bearer ".length());
        Blog blog = blogService.createBlog(blogCreation, accessToken);

        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);
        return ResponseEntity.ok(blogResponse);
    }

    @GetMapping("/{slug}")
    private ResponseEntity<BlogResponse> getBlogBySlug(@PathVariable(name = "slug") String slug) {
        Blog blog = blogService.getBlogBySlug(slug);
        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);
        return ResponseEntity.ok(blogResponse);
    }

    @PutMapping("/{slug}")
    private ResponseEntity<BlogResponse> editBlog(@PathVariable String slug, @RequestBody UpdateBlog updateBlog,
                                                  @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.substring("Bearer ".length());
        Blog blog = blogService.editBlog(slug, updateBlog, accessToken);
        BlogResponse blogResponse = modelMapper.map(blog, BlogResponse.class);
        return ResponseEntity.ok(blogResponse);
    }


    @ExceptionHandler({
            BlogService.BlogTitleException.class,
            BlogService.BlogSlugException.class,
            UserService.UserNotFoundException.class,
            UserService.InvalidUserException.class
    })
    private ResponseEntity<BlogException> handleException(Exception ex) {
        HttpStatus status;
        String message;

        if (ex instanceof BlogService.BlogTitleException) {
            status = HttpStatus.PARTIAL_CONTENT;
            message = ex.getMessage();
        } else if (ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidUserException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_ACCEPTABLE;
        } else if (ex instanceof BlogService.BlogSlugException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Something went wrong";
        }
        BlogException blogException = BlogException.builder().message(message).build();
        return ResponseEntity.status(status).body(blogException);
    }


}
