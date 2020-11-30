package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	public void addUser(){
		User user = new User();
		user.setCreateTime(new Date());
		user.setNickName("test");
		user.setPassword("123456");
		user.setUsername("zhangsan");

		userService.addUser(user);
	}

	@Test
	public void getUsers(){
		userService.getAll().forEach(System.out::println);

		userService.getAll().forEach(System.out::println);

		userService.getAll().forEach(System.out::println);

		userService.getAll().forEach(System.out::println);
	}

}
