package com.sufiyandev.BlogShare.blog.service;

import com.sufiyandev.BlogShare.blog.dto.BlogCreation;
import com.sufiyandev.BlogShare.blog.dto.UpdateBlog;
import com.sufiyandev.BlogShare.blog.model.Blog;
import com.sufiyandev.BlogShare.blog.repository.BlogRepository;
import com.sufiyandev.BlogShare.user.model.User;
import com.sufiyandev.BlogShare.user.repository.UserRepository;
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

    public Blog createBlog(BlogCreation blogCreation) {
        if (blogCreation.getTitle() == null) throw new BlogTitleException();
        Blog blog = modelMapper.map(blogCreation, Blog.class);

        // TODO: Add user to blog (set createdBy)
        User user = new User();
        user.setUsername("sufi");
        user.setPassword("1235");
        userRepository.save(user);
        blog.setCreatedBy(user);
        // Remove till here

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

    public Blog editBlog(String slug, UpdateBlog updateBlog) {
        Blog blog = getBlogBySlug(slug);
        blog = mapUpdateBlogToBlog(updateBlog, blog);
        blog.setModifiedAt(LocalDate.now());
        // TODO: set modifiedBy
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
