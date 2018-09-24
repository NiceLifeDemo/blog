package com.rczx.blog.service;

import com.rczx.blog.dto.AddBlogDTO;
import com.rczx.blog.mapper.BlogMapper;
import com.rczx.blog.model.BlogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/9/23.
 */
@Service
@Transactional
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    public void addBlog(AddBlogDTO addBlogDTO){

        BlogInfo blogInfo=new BlogInfo();
        blogInfo.setBlogName(addBlogDTO.getBlogName());
        blogInfo.setBlogTitle(addBlogDTO.getBlogTitle());
        blogInfo.setBlogLabel(addBlogDTO.getBlogLabel());
        blogInfo.setBlogContent(addBlogDTO.getBlogContent());
        blogInfo.setBlogDescription(addBlogDTO.getBlogDescription());
        String userID="1";
        blogInfo.setUserID(userID);
        blogMapper.addBlog(blogInfo);

    }


}
