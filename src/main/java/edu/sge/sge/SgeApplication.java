package edu.sge.sge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class SgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgeApplication.class, args);
	}

}
