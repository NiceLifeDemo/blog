package com.rczx.blog.controller;


import com.rczx.blog.dto.AddBlogDTO;
import com.rczx.blog.service.BlogService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.FastHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class RestUserController {

 @Autowired
  private BlogService blogService;


    @PostMapping ({"/add.json"})
    @ApiOperation(value="新增", tags={"博客管理"})
    public Map<String, Object> getUserInfo(@RequestBody AddBlogDTO addBlogDTO) {

        Map<String, Object> map=new HashMap();
        try {
            blogService.addBlog(addBlogDTO);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state","fail");
            return map;
        }

        map.put("state","suc");
        return map;
    }





}
