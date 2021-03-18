package com.telkom.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.telkom.training.model.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer>{

	Optional<EmployeeModel> getByEmployeeName(String employeeName);
	EmployeeModel getByEmployeeNameAndEmployeeAddress(String name, String address);
	Slice<EmployeeModel> getByEmployeeAddress(String address, Pageable pagination);
	
	@Transactional
	void deleteByEmployeeName(String name);
	
	// EmployeeModel is an entity (JPQL)
	@Query("select e from EmployeeModel e where e.employeeAge < ?1")
	List<EmployeeModel> getEmployeeByAge(int age);
	
	// Native Query
	@Query(value = "select * from tb_employee where employee_address "
			+ "like %?1% and employee_name like %?2%", 
			nativeQuery = true)
	List<EmployeeModel> getEmployeeByAddressAndName(String address, String name);
	
	EmployeeModel getByEmployeeEmail(String email);
	
}
