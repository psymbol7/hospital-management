package com.project.hospitalmanagement.dao;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.Patient;
import com.project.hospitalmanagement.model.request.AdmitPatientRequest;

public interface PatientDao {
	
	CompletableFuture<Result> admitPatient(AdmitPatientRequest request);
	
	CompletableFuture<List<Patient>> fetchAllPatients();
	
	CompletableFuture<Result> dischargePatient(UUID patientId);

}
