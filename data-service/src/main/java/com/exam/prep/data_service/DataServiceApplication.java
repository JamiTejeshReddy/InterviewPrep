package com.exam.prep.data_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

// Example: Exclude UploadApiController from Spring context
@SpringBootApplication
public class DataServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataServiceApplication.class, args);
	}
}