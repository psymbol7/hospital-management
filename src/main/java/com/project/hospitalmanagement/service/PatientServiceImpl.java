package com.project.hospitalmanagement.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.hospitalmanagement.dao.PatientDao;
import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.request.AdmitPatientRequest;
import com.project.hospitalmanagement.model.response.FetchAllPatientResponse;
import com.project.hospitalmanagement.model.response.Response;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	@Override
	public Mono<Response> admitPatient(AdmitPatientRequest request) {
		log.info("received request to admit patient : {}", request);
		return Mono.fromFuture(patientDao.admitPatient(request))
				.flatMap(response -> Mono.just(new Response(response)))
				.onErrorReturn(new Response(Result.DB_ERROR));
	}
	
	@Override
	public Mono<FetchAllPatientResponse> fetchAllPatients() {
		log.info("received request to fetch all patients");
		return Mono.fromFuture(patientDao.fetchAllPatients())
				.flatMap(response -> {
					if (response.isEmpty()) {
						return Mono.just(new FetchAllPatientResponse(Result.PATIENTS_NOT_FOUND));
					}
					return Mono.just(new FetchAllPatientResponse(Result.SUCCESS, response));
				}).onErrorReturn(new FetchAllPatientResponse(Result.DB_ERROR));
	}
	
	@Override
	public Mono<Response> dischargePatient(UUID patientId) {
		log.info("received request to discharge patient : {}", patientId);
		return Mono.fromFuture(patientDao.dischargePatient(patientId))
				.flatMap(response -> Mono.just(new Response(response)))
				.onErrorReturn(new Response(Result.DB_ERROR));
	}

}
