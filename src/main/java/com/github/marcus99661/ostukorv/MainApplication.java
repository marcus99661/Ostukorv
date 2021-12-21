package com.github.marcus99661.ostukorv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	@Autowired
	public CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		repository.save(new Customer("Bob", "5e60308cf1af9df9c5ab9201ba7179cc"));
		repository.save(new Customer("Martin", "1a0eb52ed41f35e243a8da8e3759e96e"));
	}
}
