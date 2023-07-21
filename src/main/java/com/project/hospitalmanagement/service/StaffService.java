package com.project.hospitalmanagement.service;

import com.project.hospitalmanagement.model.request.LoginStaffRequest;
import com.project.hospitalmanagement.model.request.StaffSignupRequest;
import com.project.hospitalmanagement.model.response.Response;

import reactor.core.publisher.Mono;

public interface StaffService {
	
	Mono<Response> signup(StaffSignupRequest request);
	
	Mono<Response> login(LoginStaffRequest request);

}
