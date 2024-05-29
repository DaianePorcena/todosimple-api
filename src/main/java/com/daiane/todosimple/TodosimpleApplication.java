package com.daiane.todosimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.daiane")
public class TodosimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosimpleApplication.class, args);
	}

}
