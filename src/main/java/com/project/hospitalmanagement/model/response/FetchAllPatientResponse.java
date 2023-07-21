package com.project.hospitalmanagement.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class FetchAllPatientResponse {
	
	private Result result;
	private List<Patient> patients;
	
	public FetchAllPatientResponse(Result result) {
		this.result = result;
	}

}
