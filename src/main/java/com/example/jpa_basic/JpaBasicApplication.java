package com.example.jpa_basic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaBasicApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpaBasicApplication.class, args);
	}

}
