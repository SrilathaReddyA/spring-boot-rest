package io.spring.rest.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.spring.rest.entity.Employee;
import io.spring.rest.exception.BadRequestException;
import io.spring.rest.exception.EmployeeNotFoundException;
import io.spring.rest.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository empRepository;
	
	public List<Employee> findAll() {
		return (List<Employee>) empRepository.findAll();
	}
	
	public Employee findEmployee(String id) {
		Optional<Employee> emp = empRepository.findById(id);
		if(!emp.isPresent()){
			throw new EmployeeNotFoundException("Employee with id " + id +" NOT FOUND");
		}else 
			return emp.get();
	}

	@Transactional
	public Employee createEmployee(Employee employee) {
		Optional<Employee> existing = empRepository.findByEmail(employee.getEmail());
		if(existing.isPresent()){
			throw new BadRequestException("Employee with email "+employee.getEmail() + " already exists");
		}else
			return empRepository.save(employee);
	}

	@Transactional
	public Employee updateEmployee(String empId, Employee employee) {
		Optional<Employee> existing = empRepository.findById(empId);
		if(!existing.isPresent()){
			throw new EmployeeNotFoundException("Employee with id "+empId + " doesn't exists");
		}
		return empRepository.save(employee);
	}

	@Transactional
	public void deleteEmployee(String empId) {
		Optional<Employee> existing = empRepository.findById(empId);
		if(!existing.isPresent()){
			throw new EmployeeNotFoundException("Employee with id "+empId + " doesn't exists");
		}
		else
			empRepository.delete(existing.get());
	}

}
