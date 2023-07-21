package com.project.hospitalmanagement.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.request.AdmitPatientRequest;
import com.project.hospitalmanagement.model.response.FetchAllPatientResponse;
import com.project.hospitalmanagement.model.response.Response;
import com.project.hospitalmanagement.service.PatientService;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/v1/patients/")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping
	public Mono<ResponseEntity<Response>> admitPatient(@Valid @RequestBody AdmitPatientRequest request) {
		if (request.getAge() < 0 || request.getRoom() < 0 || request.getExpenses() < 0) {
			return Mono.just(ResponseEntity.ok(new Response(Result.INVALID_REQUEST)));
		}
		return patientService.admitPatient(request)
				.flatMap(response -> Result.DB_ERROR.equals(response.getResult()) 
						? Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)) 
						: Mono.just(ResponseEntity.ok(response)));
	}
	
	@GetMapping
	public Mono<ResponseEntity<FetchAllPatientResponse>> fetchAllPatients() {
		return patientService.fetchAllPatients()
				.flatMap(response -> Result.DB_ERROR.equals(response.getResult()) 
						? Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)) 
						: Mono.just(ResponseEntity.ok(response)));
	}
	
	@PutMapping("{patientId}")
	public Mono<ResponseEntity<Response>> dischargePatient(@PathVariable UUID patientId) {
		return patientService.dischargePatient(patientId)
				.flatMap(response -> Result.DB_ERROR.equals(response.getResult()) 
						? Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)) 
						: Mono.just(ResponseEntity.ok(response)));
	}

}
