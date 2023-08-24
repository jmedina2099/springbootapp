package org.springboot.app;

import static org.springboot.app.constants.Constants.MSG_PRE_ERROR;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;

import lombok.Generated;

@SpringBootApplication
@Profile(value = { "dev", "prod" })
public class AppApplication extends SpringBootServletInitializer {

	private static final Logger log = LogManager.getLogger(AppApplication.class);

	@Generated
	public static void main(String[] args) {
		try {
			SpringApplication.run(AppApplication.class, args);
		} catch (Exception e) {
			log.error(MSG_PRE_ERROR, e.getMessage());
		}
	}

}