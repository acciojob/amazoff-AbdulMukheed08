package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {

	@Autowired
	static OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		//System.out.println(orderRepository.orderMap);
		//System.out.println(orderRepository.deliveryPartnerMap);
	}
}
