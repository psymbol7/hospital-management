package com.project.hospitalmanagement.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class PostgresSqlConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "write")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public HikariDataSource dataSource() {
		return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Bean
	@ConfigurationProperties("spring.read-datasource")
	public DataSourceProperties slaveDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "read")
	@ConfigurationProperties(prefix = "spring.read-datasource")
	public HikariDataSource slaveDataSource() {
		return slaveDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
}
