package com.project.hospitalmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.request.LoginStaffRequest;
import com.project.hospitalmanagement.model.request.StaffSignupRequest;
import com.project.hospitalmanagement.model.response.Response;
import com.project.hospitalmanagement.service.StaffService;
import com.project.hospitalmanagement.util.CommonUtil;
import com.project.hospitalmanagement.util.Encryption;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/v1/staffs/")
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@PostMapping("signup")
	public Mono<ResponseEntity<Response>> signup(@Valid @RequestBody StaffSignupRequest request) {
		if (!CommonUtil.isPasswordFormatValid(request.getPassword())) {
			return Mono.just(ResponseEntity.ok(new Response(Result.INVALID_PASSWORD)));
		}
		// Encoding the password before storing in the database
		request.setPassword(Encryption.encode(request.getPassword()));
		
		return staffService.signup(request)
				.flatMap(response -> Result.DB_ERROR.equals(response.getResult()) 
						? Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)) 
						: Mono.just(ResponseEntity.ok(response)));
	}
	
	@GetMapping("login")
	public Mono<ResponseEntity<Response>> login(@Valid @RequestBody LoginStaffRequest request) {
		return staffService.login(request)
				.flatMap(response -> Result.DB_ERROR.equals(response.getResult()) 
						? Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)) 
						: Mono.just(ResponseEntity.ok(response)));
	}
	
}
