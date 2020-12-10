package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
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
	private OrderService orderService;

	@Test
	public void add(){

		Order order = new Order();
		order.setCreateTime(new Date());
		order.setCustId(1L);
		order.setGoodsId(1L);
		order.setPayAmount(88);
		order.setPayTime(new Date());
		order.setState(1);

		orderService.addOrder(order);
	}

	@Test
	public void listAll(){
		orderService.getAll().forEach(System.out::println);
	}

}
