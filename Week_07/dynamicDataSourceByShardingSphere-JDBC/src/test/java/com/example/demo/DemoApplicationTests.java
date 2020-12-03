package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
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
		user.setNickName("test11");
		user.setPassword("1234567");
		user.setUsername("zhangsi");

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
