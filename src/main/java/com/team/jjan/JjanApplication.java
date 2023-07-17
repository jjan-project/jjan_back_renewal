package com.team.jjan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JjanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjanApplication.class, args);
	}

}
