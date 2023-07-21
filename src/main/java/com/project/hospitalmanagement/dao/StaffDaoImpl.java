package com.project.hospitalmanagement.dao;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.hospitalmanagement.config.JdbcTemplateProvider;
import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.Staff;
import com.project.hospitalmanagement.model.request.StaffSignupRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Async("db_executor")
public class StaffDaoImpl implements StaffDao {
	
	@Autowired
	private JdbcTemplateProvider jdbcTemplateProvider;
	
	@Override
	public CompletableFuture<Boolean> isDuplicateUsername(String username) {
		try {
			Map<String, Object> result = jdbcTemplateProvider.getJdbcReadTemplate().queryForMap(Queries.GET_USERNAME_COUNT, username);
			int count = result.get("count") != null ? Integer.parseInt(result.get("count").toString()) : 0;
			
			return CompletableFuture.completedFuture(count != 0);
		} catch (Exception ex) {
			log.error("error occured while checking if username : {} is duplicate", username, ex);
			return CompletableFuture.failedFuture(ex);
		}
	}
	
	@Override
	public CompletableFuture<Result> signup(StaffSignupRequest request) {
		try {
			int result = jdbcTemplateProvider.getJdbcWriteTemplate().update(Queries.INSERT_STAFF_DETAILS, UUID.randomUUID(), request.getUserName(), request.getPassword());
			return result == 1 ? CompletableFuture.completedFuture(Result.SUCCESS) : CompletableFuture.completedFuture(Result.DB_ERROR);
		} catch (Exception ex) {
			log.error("error occured while inserting hospital staff details : {}", request, ex);
			return CompletableFuture.failedFuture(ex);
		}
	}
	
	@Override
	public CompletableFuture<Staff> getStaffDetails(String username) {
		try {
			Map<String, Object> result = jdbcTemplateProvider.getJdbcReadTemplate().queryForMap(Queries.GET_STAFF_DETAILS, username);
			return CompletableFuture.completedFuture(new Staff(Result.SUCCESS,
					result.get("user_name") != null ? result.get("user_name").toString() : "",
					result.get("password") != null ? result.get("password").toString() : ""));
		} catch (EmptyResultDataAccessException ex) {
			log.error("error occured while fetching hospital staff details as username : {} doesn't exist", username);
			return CompletableFuture.completedFuture(new Staff(Result.STAFF_NOT_FOUND));
		} catch (Exception ex) {
			log.error("error occured while fetching hospital staff : {} details", username, ex);
			return CompletableFuture.failedFuture(ex);
		}
	}

}
