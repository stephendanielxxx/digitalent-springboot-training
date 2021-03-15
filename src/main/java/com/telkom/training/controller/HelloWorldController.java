package com.telkom.training.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "Hello Stephen";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
		return "Email "+email+", password "+password;
	}
	
	@PostMapping("/testEmployee")
	public String createEmployee(@RequestBody String employee) {
		return "Employee "+employee;
	}
	
}
