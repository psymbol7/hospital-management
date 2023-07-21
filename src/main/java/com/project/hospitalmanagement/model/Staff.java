package com.project.hospitalmanagement.model;

import com.project.hospitalmanagement.enums.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
	
	private Result result;
	private String userName;
	private String password;
	
	public Staff(Result result) {
		this.result = result;
	}
	
}
