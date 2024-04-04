package com.matheusmangueira.microserviceusers;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MicroserviceUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUsersApplication.class, args);
	}

}
