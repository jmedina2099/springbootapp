package org.springboot.app.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.Generated;

/**
 * @author jmedina
 *
 */
@Component
@Generated
public class ApplicationContextProvider implements ApplicationContextAware {

	private final Logger logger = LogManager.getLogger(ApplicationContextProvider.class);

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.logger.info("================> applicationContext LOADED!");
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}