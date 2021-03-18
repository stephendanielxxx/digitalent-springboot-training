package com.telkom.training.responses;

import com.telkom.training.model.EmployeeModel;

public class EmployeeResponse extends BaseResponse{
	private EmployeeModel employee;

	public EmployeeModel getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeModel employee) {
		this.employee = employee;
	}
	
}
