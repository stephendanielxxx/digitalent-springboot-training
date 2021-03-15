package com.telkom.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telkom.training.model.EmployeeModel;
import com.telkom.training.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeModel createEmployee(EmployeeModel employeeModel) {
		return employeeRepository.save(employeeModel);
	}
	
	public EmployeeModel getEmployee(int id) {
		return employeeRepository.findById(id).get();
	}
	
	public EmployeeModel getEmployee(String name) {
		return employeeRepository.getByEmployeeName(name);
	}
	
	public EmployeeModel getEmployee(String name, String address) {
		return employeeRepository.getByEmployeeNameAndEmployeeAddress(name, address);
	}
	
	public Iterable<EmployeeModel> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	public Iterable<EmployeeModel> getEmployeesByAddress(String address){
		return employeeRepository.getByEmployeeAddress(address);
	}
}
