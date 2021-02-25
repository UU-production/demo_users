package com.uu_demo.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ComponentScan("com.uu_demo")
@EntityScan("com.uu_demo.models.entity")
@SpringBootApplication
@ContextConfiguration
public class UuDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UuDemoApplication.class, args);
	}


}

