package com.project.hospitalmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Patient {
	
	private UUID id;
	private String name;
	private int age;
	private int room;
	private String doctorName;
	private double expenses;
	private String status;
	private LocalDateTime admitDate;

}
