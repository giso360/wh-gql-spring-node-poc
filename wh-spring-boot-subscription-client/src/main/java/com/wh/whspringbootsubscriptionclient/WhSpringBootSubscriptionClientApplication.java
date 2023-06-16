package com.wh.whspringbootsubscriptionclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhSpringBootSubscriptionClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WhSpringBootSubscriptionClientApplication.class, args);
		new WebSocketRunner().run();
	}
}
