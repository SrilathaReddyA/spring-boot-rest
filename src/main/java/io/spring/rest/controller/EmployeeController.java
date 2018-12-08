package io.spring.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.spring.rest.entity.Employee;
import io.spring.rest.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//swagger paths

//http://localhost:8080/api/v2/api-docs
//http://localhost:8080/api/swagger-ui.html#/employee-controller
@RestController
@RequestMapping(value = "employees")
@Api(description = "Employee related endpoints")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Find all employees",
			notes = "Returns the list of employees available in the database")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public List<Employee> finaAll(){
		return service.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Find employee by ID",
			notes = "Returns the employee Object or throws 404")
	@ApiResponses(value = {
				@ApiResponse(code = 200, message = "OK"),
				@ApiResponse(code = 404, message = "Not Found"),
				@ApiResponse(code = 500, message = "Internal Server Error")
	})
	public Employee findEmployee(@ApiParam(value = "id of the Employee", required = true) @PathVariable("id") String empId){
		return service.findEmployee(empId);
	}
	
	@RequestMapping(method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee createEmployee(@RequestBody Employee employee){
		return service.createEmployee(employee);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "{id}", 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Employee updateEmployee(@PathVariable("id") String empId, @RequestBody Employee employee){
		return service.updateEmployee(empId, employee);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public void deleteEmployee(@PathVariable("id") String empId){
		service.deleteEmployee(empId);
	}
}
