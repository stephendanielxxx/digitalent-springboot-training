package com.telkom.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telkom.training.dto.EmployeeDTO;
import com.telkom.training.model.EmployeeModel;
import com.telkom.training.responses.AllEmployeeResponse;
import com.telkom.training.responses.EmployeeResponse;
import com.telkom.training.responses.TelkomResponse;
import com.telkom.training.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/employee/register")
	public EmployeeModel createEmployee(@RequestBody EmployeeModel employeeModel) {
		return employeeService.createEmployee(employeeModel);
	}
	
	@GetMapping("/employee/{id}")
	public EmployeeResponse getEmployee(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}
	
	@GetMapping("/employee/name/{name}")
	public TelkomResponse<EmployeeDTO> getEmployeeByName(@PathVariable String name) {
		return employeeService.getEmployee(name);
	}
	
	@GetMapping("/employee/detail")
	public EmployeeModel getEmployee(@RequestParam("name") String name,
			@RequestParam("address") String address) {
		return employeeService.getEmployee(name, address);
	}
	
	@GetMapping("/employee/all")
	public AllEmployeeResponse getAllEmployee(){
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("/employee/all/{address}")
	public TelkomResponse<Slice<EmployeeModel>> getEmployeeByAddress(@PathVariable String address,
			@RequestParam("page") int page, 
			@RequestParam("size") int size){
		return employeeService.getEmployeesByAddress(address, page, size);
	}
	
	@PutMapping("/employee/update/{id}")
	public EmployeeModel updateEmployee(@PathVariable int id, @RequestBody EmployeeModel employeeModel) {
		return employeeService.updateEmployee(employeeModel, id);
	}
	
	@PatchMapping("/employee/patch/{id}")
	public EmployeeModel patchEmployee(@PathVariable int id, @RequestBody EmployeeModel employeeModel) {
		return employeeService.patchEmployee(employeeModel, id);
	}
	
	@DeleteMapping("/employee/delete/{id}")
	public EmployeeModel deleteEmployee(@PathVariable int id) {
		return employeeService.deleteEmployee(id);
	}
	
	@DeleteMapping("/employee/delete/name/{name}")
	public boolean deleteEmployeeByName(@PathVariable String name) {
		employeeService.deleteEmployeeByName(name);
		return true;
	}
	
	@PostMapping("/uploadFile")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) {
		return employeeService.saveFile(file);
	}
	
	@PostMapping("/employee/upload")
	public boolean uploadFile(@RequestParam("file") MultipartFile file, 
			@RequestParam("employeeId") int employeeId) {
		return employeeService.saveFile(file, employeeId);
	}
	
	@DeleteMapping("/employee/delete/all/crud")
	public boolean deleteAllCrud() {
		employeeService.deleteEmployeeCrud();
		return true;
	}
	
	@DeleteMapping("/employee/delete/all/jpa")
	public boolean deleteAllJpa() {
		employeeService.deleteEmployeeJPA();
		return true;
	}
	
	@GetMapping("/employee/all/page")
	public Page<EmployeeModel> getEmployeeByPage(@RequestParam("page") int page,
			@RequestParam("size") int size){
		return employeeService.getEmployeeByPage(page, size);
	}
	
	@GetMapping("/employee/age/{age}")
	public List<EmployeeModel> getEmployeeByAge(@PathVariable int age){
		return employeeService.getEmployeeByAge(age);
	}
	
	@GetMapping("/employee/filter")
	public List<EmployeeDTO> getEmployeeByAddressAndName(
			@RequestParam("address") String address,
			@RequestParam("name") String name){
		return employeeService.getEmployeeByAddressAndName(address, name);
	}
}



