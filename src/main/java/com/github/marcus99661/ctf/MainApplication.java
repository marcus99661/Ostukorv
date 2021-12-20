package com.github.marcus99661.ctf;


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

	static String FLAG = "ERROR FLAG FILE";

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		try {
			Path fileName = Path.of("/var/backup/secret.txt");
			FLAG = Files.readString(fileName);
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Bob", "5e60308cf1af9df9c5ab9201ba7179cc"));
		repository.save(new Customer("admin", FLAG));
		repository.save(new Customer("Martin", "1a0eb52ed41f35e243a8da8e3759e96e"));

		// fetch all customers
		/*
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByName("Bob")) {
			System.out.println(customer);
		}
		*/
	}
}
