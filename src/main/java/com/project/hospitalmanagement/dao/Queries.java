package com.project.hospitalmanagement.dao;

public class Queries {
	
	private Queries() {}
	
	public static final String GET_USERNAME_COUNT = "select count(*) from staff where user_name = ?";
	
	public static final String INSERT_STAFF_DETAILS = "insert into staff (staff_id, user_name, password) values (?,?,?)";
	
	public static final String GET_STAFF_DETAILS = "select user_name, password from staff where user_name = ?";
	
	// created_at column is holding the data for admit date of a patient
	public static final String INSERT_PATIENT_DETAILS = "insert into patient (patient_id, name, age, room, doctor_name, expenses, status) values (?,?,?,?,?,?,?)";
	
	public static final String GET_ALL_PATIENT_DETAILS = "select patient_id, name, age, room, doctor_name, expenses, status, created_at from patient";
	
	public static final String MARK_PATIENT_DISCHARGED = "update patient set status = 2, updated_at = now() where patient_id = ?";

}
