package com.telkom.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telkom.training.model.EmployeeModel;
import com.telkom.training.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/employee")
	public EmployeeModel createEmployee(@RequestBody EmployeeModel employeeModel) {
		return employeeService.createEmployee(employeeModel);
	}
	
	@GetMapping("/employee/{id}")
	public EmployeeModel getEmployee(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}
	
	@GetMapping("/employee/name/{name}")
	public EmployeeModel getEmployeeByName(@PathVariable String name) {
		return employeeService.getEmployee(name);
	}
	
	@GetMapping("/employee/detail")
	public EmployeeModel getEmployee(@RequestParam("name") String name,
			@RequestParam("address") String address) {
		return employeeService.getEmployee(name, address);
	}
	
	@GetMapping("/employee/all")
	public Iterable<EmployeeModel> getAllEmployee(){
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("/employee/all/{address}")
	public Iterable<EmployeeModel> getEmployeeByAddress(@PathVariable String address){
		return employeeService.getEmployeesByAddress(address);
	}
}

