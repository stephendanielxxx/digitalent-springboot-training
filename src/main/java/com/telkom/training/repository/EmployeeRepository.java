package com.telkom.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.telkom.training.model.EmployeeModel;

public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer>{

	EmployeeModel getByEmployeeName(String employeeName);
	EmployeeModel getByEmployeeNameAndEmployeeAddress(String name, String address);
	Iterable<EmployeeModel> getByEmployeeAddress(String address);
}
