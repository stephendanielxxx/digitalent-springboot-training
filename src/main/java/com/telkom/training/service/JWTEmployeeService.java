package com.telkom.training.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telkom.training.model.EmployeeModel;
import com.telkom.training.repository.EmployeeRepository;

@Service
public class JWTEmployeeService implements UserDetailsService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		EmployeeModel employeeModel = employeeRepository.getByEmployeeEmail(email);
		if(employeeModel != null) {
			return new User(employeeModel.getEmployeeEmail(), employeeModel.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("User not found");
		}
	}

}
