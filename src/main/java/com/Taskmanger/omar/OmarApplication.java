package com.Taskmanger.omar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(
		info = @Info(title = "My API", version = "v1")
)public class OmarApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmarApplication.class, args);
	}


}
