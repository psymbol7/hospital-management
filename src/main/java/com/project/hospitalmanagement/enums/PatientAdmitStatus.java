package com.project.hospitalmanagement.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum PatientAdmitStatus {
	
	ADMITTED(1),
	DISCHARGED(2);
	
	private int id;
	private static Map<Integer, PatientAdmitStatus> getStatusById = new HashMap<>();
	
	private PatientAdmitStatus(int id) {
		this.id = id;
	}
	
	static {
		for (PatientAdmitStatus status : PatientAdmitStatus.values()) {
			getStatusById.put(status.getId(), status);
		}
	}
	
	public static PatientAdmitStatus getStatusById(int id) {
		return getStatusById.get(id);
	}

}
