package com.roncoo.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemo171ApplicationTests {
	// 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。
	private MockMvc mockMvc;

	@Autowired
	// 注入WebApplicationContext
	private WebApplicationContext wac;

	@Before
	public void setup() {
		// MockMvcBuilders使用构建MockMvc对象 （项目拦截器有效）
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		// 单个类 拦截器无效
		// mockMvc = MockMvcBuilders.standaloneSteup(userController).build();
	}

	@Test
	public void redisAddTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("localhost:8080/api/redis/add/user2/kitty");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
}
