package com.rczx;

import com.rczx.blog.BlogApplication;
import com.rczx.blog.config.MyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class BlogApplicationTests {

	@Autowired
	private MyConfig myConfig;


	@Test
	public void testDisplayPropsValue() {

		String wechatUrl=myConfig.getWechatUrl();

		System.out.println(wechatUrl);

	}
}
