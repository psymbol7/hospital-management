package com.project.hospitalmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class Config {
	
	@Value("${db-async-executor.pool-size.core}")
	private int dbAsyncCorePoolSize;
	
	@Value("${db-async-executor.pool-size.max}")
	private int dbAsyncMaxPoolSize;

}
