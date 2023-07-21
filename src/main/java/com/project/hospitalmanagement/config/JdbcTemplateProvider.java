package com.project.hospitalmanagement.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcTemplateProvider {
	
	@Autowired
	@Qualifier("read")
	private DataSource readDataSource;
	
	@Autowired
	@Qualifier("write")
	private DataSource writeDataSource;
	
	private JdbcTemplate readTemplate;
	private JdbcTemplate writeTemplate;
	
	@PostConstruct
	private void init() {
		this.readTemplate = new JdbcTemplate(readDataSource);
		this.writeTemplate = new JdbcTemplate(writeDataSource);
	}

	public JdbcTemplate getJdbcReadTemplate() {
		return readTemplate;
	}
	
	public JdbcTemplate getJdbcWriteTemplate() {
		return writeTemplate;
	}

}
