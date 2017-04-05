package com.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
//@EnableTransactionManagement
@SpringBootApplication
public class PsAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsAccountApplication.class, args);
	}
}
