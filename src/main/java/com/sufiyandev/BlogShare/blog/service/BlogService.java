package com.sufiyandev.BlogShare.blog.service;

import com.sufiyandev.BlogShare.blog.dto.BlogCreation;
import com.sufiyandev.BlogShare.blog.dto.UpdateBlog;
import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.blog.repository.BlogRepository;
import com.sufiyandev.BlogShare.security.JWTService;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
import com.sufiyandev.BlogShare.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class BlogService {

    private final Long likes = 0L;

    @Autowired
    private BlogRepository blogRepository;

    // TODO: Remove UserRepository dependency from here. It was temporirily added for creating & fetching user
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTService jwtService;

    public Blog createBlog(BlogCreation blogCreation, String accessToken) {
        if (blogCreation.getTitle() == null) throw new BlogTitleException();
        Blog blog = modelMapper.map(blogCreation, Blog.class);

        Long userId = jwtService.retrieveUserId(accessToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserService.UserNotFoundException(userId));
        blog.setCreatedBy(user);

        blog.setCreatedAt(LocalDate.now());
        blog.setSlug(blogCreation.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        blog.setLikes(likes);
        blogRepository.save(blog);
        return blog;
    }

    public Blog getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBlogBySlug(slug).orElseThrow(() -> new BlogSlugException(slug));
        return blog;
    }

    private Blog mapUpdateBlogToBlog(UpdateBlog updateBlog, Blog blog) {
        if(updateBlog.getTitle() != null) {
            blog.setTitle(updateBlog.getTitle());
        }
        if (updateBlog.getSlug() != null) {
            blog.setSlug(updateBlog.getSlug());
        }
        if (updateBlog.getBody() != null) {
            blog.setBody(updateBlog.getBody());
        }
        return blog;
    }

    public Blog editBlog(String slug, UpdateBlog updateBlog, String accessToken) {
        Long userId = jwtService.retrieveUserId(accessToken);
        Blog blog = getBlogBySlug(slug);
        // validating that user who Created this blog can only modify it.
        if(blog.getCreatedBy().getId() != userId) throw new UserService.InvalidUserException(blog.getCreatedBy().getId());
        blog = mapUpdateBlogToBlog(updateBlog, blog);
        blog.setModifiedAt(LocalDate.now());
        blog.setModifiedBy(blog.getCreatedBy());
        return blog;
    }



    public static class BlogTitleException extends IllegalArgumentException {
        public BlogTitleException() {
            super("Blog's title can not be empty");
        }
    }

    public static class BlogSlugException extends IllegalArgumentException {
        public BlogSlugException(String slug) {
            super("Slug: " + slug + " does not exist");
        }
    }

}
