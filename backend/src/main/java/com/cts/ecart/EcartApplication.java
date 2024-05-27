package com.cts.ecart;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
public class EcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcartApplication.class, args);
		log.info("-------------Application started successfully------------");
	}

}
