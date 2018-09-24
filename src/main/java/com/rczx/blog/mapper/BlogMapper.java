package com.rczx.blog.mapper;

import com.rczx.blog.model.BlogInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/9/23.
 */
@Mapper
public interface BlogMapper {

    public void addBlog(BlogInfo blogInfo);

}
