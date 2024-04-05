package com.matheusmangueira.microserviceTransfer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MicroserviceTransferApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroserviceTransferApplication.class, args);
  }

}
