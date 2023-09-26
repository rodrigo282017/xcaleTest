package com.xcale.xcaletest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class XcaleTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(XcaleTestApplication.class, args);
	}

}
