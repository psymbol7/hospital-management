package com.project.hospitalmanagement.model.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StaffSignupRequest {
	
	@NotEmpty
	@JsonProperty("user_name")
	private String userName;
	
	@NotEmpty
	private String password;
	
}
