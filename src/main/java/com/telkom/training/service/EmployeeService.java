package com.telkom.training.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telkom.training.dto.EmployeeDTO;
import com.telkom.training.model.EmployeeModel;
import com.telkom.training.repository.EmployeeRepository;
import com.telkom.training.responses.AllEmployeeResponse;
import com.telkom.training.responses.EmployeeResponse;
import com.telkom.training.responses.TelkomResponse;
import com.telkom.training.utils.ResponseCode;
import com.telkom.training.utils.ResponseMessage;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public EmployeeModel createEmployee(EmployeeModel employeeModel) {
		employeeModel.setPassword(bCryptPasswordEncoder.encode(employeeModel.getPassword()));
		return employeeRepository.save(employeeModel);
	}
	
	public EmployeeResponse getEmployee(int id) {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);
		if(currentEmployee.isPresent()) {
			employeeResponse.setCode(ResponseCode.SUCCESS);
			employeeResponse.setMessage(ResponseMessage.SUCCESS);
			employeeResponse.setEmployee(currentEmployee.get());
		}else {
			employeeResponse.setCode(ResponseCode.FAILED);
			employeeResponse.setMessage(ResponseMessage.FAILED);
		}
		return employeeResponse;
	}
	
	public EmployeeModel getEmployee(String name, String address) {
		return employeeRepository.getByEmployeeNameAndEmployeeAddress(name, address);
	}
	
	public AllEmployeeResponse getAllEmployee(){
		AllEmployeeResponse allEmployeeResponse = new AllEmployeeResponse();
		allEmployeeResponse.setCode(ResponseCode.SUCCESS);
		allEmployeeResponse.setMessage(ResponseMessage.SUCCESS);
		allEmployeeResponse.setEmployeeModels(employeeRepository.findAll(Sort.by(Order.asc("employeeAge"))));
		return allEmployeeResponse;
	}
	
	public TelkomResponse<Slice<EmployeeModel>> getEmployeesByAddress(String address, int page, int size){
		Pageable pagination = PageRequest.of(page, size);
		
		TelkomResponse<Slice<EmployeeModel>> response = new TelkomResponse<>();
		response.setCode(ResponseCode.SUCCESS);
		response.setMessage(ResponseMessage.SUCCESS);
		response.setData(employeeRepository.getByEmployeeAddress(address, pagination));
		return response;
	}
	
	public EmployeeModel updateEmployee(EmployeeModel employeeModel, int id) {
		Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);
		if(currentEmployee.isPresent()) {
			return employeeRepository.save(employeeModel);
		}else {
			return null;
		}
	}
	
	public EmployeeModel patchEmployee(EmployeeModel employeeModel, int id) {
		Optional<EmployeeModel> currentEmployee = employeeRepository.findById(id);
		if(currentEmployee.isPresent()) {
			EmployeeModel currEmployee = currentEmployee.get();
			if(employeeModel.getEmployeeName() != null && !employeeModel.getEmployeeName().isEmpty()) {
				currEmployee.setEmployeeName(employeeModel.getEmployeeName());
			}
			
			if(employeeModel.getEmployeeEmail() != null && !employeeModel.getEmployeeEmail().isEmpty()) {
				currEmployee.setEmployeeEmail(employeeModel.getEmployeeEmail());
			}
			
			if(employeeModel.getEmployeeAddress() != null && !employeeModel.getEmployeeAddress().isEmpty()) {
				currEmployee.setEmployeeAddress(employeeModel.getEmployeeAddress());
			}
			
			if(employeeModel.getEmployeeAge() != 0) {
				currEmployee.setEmployeeAge(employeeModel.getEmployeeAge());
			}
			
			return employeeRepository.save(currEmployee);
		}else {
			return null;
		}
	}
	
	public EmployeeModel deleteEmployee(int employeeId) {
		Optional<EmployeeModel> currentEmployee = employeeRepository.findById(employeeId);
		if(currentEmployee.isPresent()) {
			employeeRepository.deleteById(employeeId);
			return currentEmployee.get();
		}else {
			return null;
		}
	}
	
	public void deleteEmployeeByName(String employeeName) {
		employeeRepository.deleteByEmployeeName(employeeName);
	}
	
	private final Path folderUpload = Paths.get("uploads");
	
	public boolean saveFile(MultipartFile file) {
		try {
			if(!Files.exists(folderUpload)) {
				Files.createDirectories(folderUpload);
			}
			
			Files.copy(file.getInputStream(), this.folderUpload.resolve(file.getOriginalFilename()));
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveFile(MultipartFile file, int employeeId) {
		try {
			Optional<EmployeeModel> currentEmployee = employeeRepository.findById(employeeId);
			if(currentEmployee.isPresent()) {
				if(!Files.exists(folderUpload)) {
					Files.createDirectories(folderUpload);
				}
				
				String fileName = currentEmployee.get().getEmployeeName()
						+getExtension(file.getOriginalFilename());
				Files.copy(file.getInputStream(), this.folderUpload.resolve(fileName),
						StandardCopyOption.REPLACE_EXISTING);
				
				currentEmployee.get().setEmployeeImage(fileName);
				employeeRepository.save(currentEmployee.get());
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	private String getExtension(String filename) {
		String extension = "";
		String[] arr = filename.split("\\.");
		if(arr.length > 0) {
			extension = arr[arr.length - 1];
			return "."+extension;
		}else {
			return extension;
		}
	}
	
	public TelkomResponse<EmployeeDTO> getEmployee(String name) {
		TelkomResponse<EmployeeDTO> response = new TelkomResponse<>();
		
		Optional<EmployeeModel> currentEmployee = employeeRepository.getByEmployeeName(name);
		if(currentEmployee.isPresent()) {
			response.setCode(ResponseCode.SUCCESS);
			response.setMessage(ResponseMessage.SUCCESS);
			response.setData(currentEmployee.map(this::convertToDTOMapper).get());
		}else {
			response.setCode(ResponseCode.FAILED);
			response.setMessage(ResponseMessage.FAILED);
		}
		return response;
	}
	
	// convert to DTO manually
//	private EmployeeDTO convertToDTO(EmployeeModel employeeModel) {
//		EmployeeDTO employeeDTO = new EmployeeDTO();
//		employeeDTO.setEmployeeAddress(employeeModel.getEmployeeAddress());
//		employeeDTO.setEmployeeEmail(employeeModel.getEmployeeEmail());
//		employeeDTO.setEmployeeName(employeeModel.getEmployeeName());
//		return employeeDTO;
//	}
	
	@Autowired
	private ModelMapper modelMapper;
	
	// model mapper
	private EmployeeDTO convertToDTOMapper(EmployeeModel employeeModel) {
		modelMapper.getConfiguration().setMatchingStrategy(
				MatchingStrategies.LOOSE);
		EmployeeDTO employeeDTO = modelMapper.map(employeeModel, 
				EmployeeDTO.class);
		return employeeDTO;
	}
	
	public void deleteEmployeeCrud() {
		employeeRepository.deleteAll();
	}
	
	public void deleteEmployeeJPA() {
//		employeeRepository.deleteAllInBatch();
	}
	
	public Page<EmployeeModel> getEmployeeByPage(int page, int size){
		Pageable pagination = PageRequest.of(page, size);
		return employeeRepository.findAll(pagination);
	}
	
	public List<EmployeeModel> getEmployeeByAge(int age){
		return employeeRepository.getEmployeeByAge(age);
	}
	
	public List<EmployeeDTO> getEmployeeByAddressAndName(String address,
			String name){
		return employeeRepository.getEmployeeByAddressAndName(address, name)
				.stream().map(this::convertToDTOMapper)
				.collect(Collectors.toList());
	}
}






