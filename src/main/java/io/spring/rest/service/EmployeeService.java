package io.spring.rest.service;

import java.util.List;

import io.spring.rest.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	Employee findEmployee(String id);
	Employee createEmployee(Employee employee);
	Employee updateEmployee(String empId, Employee employee);
	void deleteEmployee(String empId);
}
