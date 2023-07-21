package com.project.hospitalmanagement.service;

import java.util.UUID;

import com.project.hospitalmanagement.model.request.AdmitPatientRequest;
import com.project.hospitalmanagement.model.response.FetchAllPatientResponse;
import com.project.hospitalmanagement.model.response.Response;

import reactor.core.publisher.Mono;

public interface PatientService {
	
	Mono<Response> admitPatient(AdmitPatientRequest request);
	
	Mono<FetchAllPatientResponse> fetchAllPatients();
	
	Mono<Response> dischargePatient(UUID patientId);

}
