package com.rczx.blog.controller.tms;


import com.rczx.blog.config.MyConfig;
import com.rczx.blog.dto.AddBlogDTO;
import com.rczx.blog.service.BlogService;
import com.rczx.blog.util.Common;
import com.rczx.blog.util.restfulbody.message.dosser.DosserReturnBody;
import com.rczx.blog.util.restfulbody.message.dosser.DosserReturnBodyBuilder;
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
public class BaseBlogController {

 @Autowired
  private BlogService blogService;




    @PostMapping ({"/add.json"})
    @ApiOperation(value="新增", tags={"博客管理"})
    public DosserReturnBody getUserInfo(@RequestBody AddBlogDTO addBlogDTO) {

        String  url= Common.WECHAT_REDIRECT_URL;

        try {
            blogService.addBlog(addBlogDTO);
        } catch (Exception e) {
            e.printStackTrace();

            return new DosserReturnBodyBuilder().statusInternalServerError().build();
        }
        return new DosserReturnBodyBuilder().statusAccpted().build();

    }





}
