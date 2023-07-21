package com.project.hospitalmanagement.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.hospitalmanagement.config.JdbcTemplateProvider;
import com.project.hospitalmanagement.enums.PatientAdmitStatus;
import com.project.hospitalmanagement.enums.Result;
import com.project.hospitalmanagement.model.Patient;
import com.project.hospitalmanagement.model.request.AdmitPatientRequest;
import com.project.hospitalmanagement.util.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Async("db_executor")
public class PatientDaoImpl implements PatientDao {
	
	@Autowired
	private JdbcTemplateProvider jdbcTemplateProvider;
	
	@Override
	public CompletableFuture<Result> admitPatient(AdmitPatientRequest request) {
		try {
			// We're not passing admit date as that would be the same when the record gets created in the table
			int result = jdbcTemplateProvider.getJdbcWriteTemplate().update(Queries.INSERT_PATIENT_DETAILS, UUID.randomUUID(), request.getName(),
					request.getAge(), request.getRoom(), request.getDoctorName(), request.getExpenses(), PatientAdmitStatus.ADMITTED.getId());
			return result == 1 ? CompletableFuture.completedFuture(Result.SUCCESS) : CompletableFuture.completedFuture(Result.DB_ERROR);
		} catch (Exception ex) {
			log.error("error occured while inserting patient details : {}", request, ex);
			return CompletableFuture.failedFuture(ex);
		}
	}
	
	@Override
	public CompletableFuture<List<Patient>> fetchAllPatients() {
		try {
			List<Map<String, Object>> result = jdbcTemplateProvider.getJdbcReadTemplate().queryForList(Queries.GET_ALL_PATIENT_DETAILS);
			List<Patient> patients = result.stream()
					.map(response -> {
						Patient patient = new Patient();
						patient.setId(response.get("patient_id") != null ? UUID.fromString(response.get("patient_id").toString()) : null);
						patient.setName(response.get("name") != null ? response.get("name").toString() : "");
						patient.setAge(response.get("age") != null ? Integer.parseInt(response.get("age").toString()) : 0);
						patient.setRoom(response.get("room") != null ? Integer.parseInt(response.get("room").toString()) : 0);
						patient.setDoctorName(response.get("doctor_name") != null ? response.get("doctor_name").toString() : "");
						patient.setExpenses(response.get("expenses") != null ? Double.parseDouble(response.get("expenses").toString()) : 0.0);
						patient.setStatus(response.get("status") != null ? PatientAdmitStatus.getStatusById(Integer.parseInt(response.get("status").toString())).name() : "");
						patient.setAdmitDate(response.get("created_at") != null ? LocalDateTime.parse(response.get("created_at").toString(), Constant.DATE_TIME_FORMATTER) : null);
						return patient;
					}).collect(Collectors.toList());
			
			return CompletableFuture.completedFuture(patients);
		} catch (Exception ex) {
			log.error("error occured while fetching all patients", ex);
			return CompletableFuture.failedFuture(ex);
		}
	}
	
	@Override
	public CompletableFuture<Result> dischargePatient(UUID patientId) {
		try {
			int result = jdbcTemplateProvider.getJdbcWriteTemplate().update(Queries.MARK_PATIENT_DISCHARGED, patientId);
			return result == 1 ? CompletableFuture.completedFuture(Result.SUCCESS) : CompletableFuture.completedFuture(Result.DB_ERROR);
		} catch (Exception ex) {
			log.error("error occured while marking patient discharged with id : {}", patientId, ex);
			return CompletableFuture.failedFuture(ex);
		}
	}

}
