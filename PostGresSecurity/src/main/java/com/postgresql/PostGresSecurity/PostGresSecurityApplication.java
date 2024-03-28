package com.postgresql.PostGresSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
public class PostGresSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostGresSecurityApplication.class, args);
	}

}
