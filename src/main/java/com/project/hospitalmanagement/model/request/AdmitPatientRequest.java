package com.project.hospitalmanagement.model.request;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AdmitPatientRequest {
	
	/**
	 * We've not taken status & admit date from client as 
	 * by default status would be 'ADMITTED' & admit date
	 * would be the time when client hits this API. 
	 */
	
	@NotEmpty
	private String name;
	
	private int age; // in years 
	
	private int room;
	
	@NotEmpty
	@JsonProperty("doctor_name")
	private String doctorName;
	
	private double expenses;

}
