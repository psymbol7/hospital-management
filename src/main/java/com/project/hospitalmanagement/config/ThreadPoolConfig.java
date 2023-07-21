package com.project.hospitalmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
	
	@Autowired
	private Config config;
	
	/**
	 * Since PostgreSQL doesn't have support for reactive drivers,
	 * we've created thread pool configuration for making our
	 * database calls in an asynchronous manner. 
	 */
	@Bean(name = "db_executor")
	public TaskExecutor dbExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(config.getDbAsyncCorePoolSize());
		executor.setMaxPoolSize(config.getDbAsyncMaxPoolSize());
		executor.setThreadNamePrefix("async-db-response");
		executor.initialize();
		return executor;
	}

}
