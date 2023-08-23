package org.springboot.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile(value = { "dev", "prod" })
public class AppApplication extends SpringBootServletInitializer {

	private static final Logger log = LogManager.getLogger(AppApplication.class);

	public static void main(String[] args) {
		log.info("=========> Starting spring boot app..");
		SpringApplication.run(AppApplication.class, args);
	}

}