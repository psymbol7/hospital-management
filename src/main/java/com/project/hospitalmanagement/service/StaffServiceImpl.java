package com.project.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalmanagement.dao.StaffDao;
import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.request.LoginStaffRequest;
import com.project.hospitalmanagement.model.request.StaffSignupRequest;
import com.project.hospitalmanagement.model.response.Response;
import com.project.hospitalmanagement.util.Encryption;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDao staffDao;
	
	public Mono<Response> signup(StaffSignupRequest request) {
		log.info("received request to sign-up hospital staff: {}", request);
		// We'll store the user name in lower case for uniformity
		request.setUserName(request.getUserName().trim().toLowerCase());
		
		// We won't allow two staff to have the same user name
		return Mono.fromFuture(staffDao.isDuplicateUsername(request.getUserName()))
				.flatMap(response -> {
					if (Boolean.TRUE.equals(response)) {
						return Mono.just(new Response(Result.DUPLICATE_STAFF_NAME));
					}
					// Adding user in our database
					return Mono.fromFuture(staffDao.signup(request))
							.flatMap(result -> Mono.just(new Response(result)))
							.onErrorReturn(new Response(Result.DB_ERROR));
				}).onErrorReturn(new Response(Result.DB_ERROR));
	}
	
	public Mono<Response> login(LoginStaffRequest request) {
		log.info("received request to login hospital staff: {}", request);
		
		// We're fetching data of the staff for validating the login request
		return Mono.fromFuture(staffDao.getStaffDetails(request.getUserName().trim().toLowerCase()))
				.flatMap(response -> {
					if (!Result.SUCCESS.equals(response.getResult())) {
						return Mono.just(new Response(response.getResult()));
					}
					// Decoding the password for verification
					if (!Encryption.verifyHash(request.getPassword(), response.getPassword())) {
						return Mono.just(new Response(Result.INVALID_PASSWORD));
					}
					// Returning positive response to the client
					return Mono.just(new Response(Result.SUCCESS));
				}).onErrorReturn(new Response(Result.DB_ERROR));
	}
	
}
