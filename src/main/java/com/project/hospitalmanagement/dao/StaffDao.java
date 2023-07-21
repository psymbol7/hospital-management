package com.project.hospitalmanagement.dao;

import java.util.concurrent.CompletableFuture;

import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.Staff;
import com.project.hospitalmanagement.model.request.StaffSignupRequest;

public interface StaffDao {
	
	CompletableFuture<Boolean> isDuplicateUsername(String username);
	
	CompletableFuture<Result> signup(StaffSignupRequest request);
	
	CompletableFuture<Staff> getStaffDetails(String username);

}
