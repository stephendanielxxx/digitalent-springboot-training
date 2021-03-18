package com.telkom.training.responses;

import com.telkom.training.model.EmployeeModel;

public class AllEmployeeResponse extends BaseResponse{

	private Iterable<EmployeeModel> employeeModels;

	public Iterable<EmployeeModel> getEmployeeModels() {
		return employeeModels;
	}

	public void setEmployeeModels(Iterable<EmployeeModel> employeeModels) {
		this.employeeModels = employeeModels;
	}
	
}
