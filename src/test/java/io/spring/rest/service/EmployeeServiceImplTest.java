package io.spring.rest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.rest.entity.Employee;
import io.spring.rest.exception.BadRequestException;
import io.spring.rest.exception.EmployeeNotFoundException;
import io.spring.rest.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

	@TestConfiguration
	static class EmployeeServiceImplTestConfiguration {
		
		@Bean
		public EmployeeService getService(){
			return new EmployeeServiceImpl();
		}
	}
	
	 List<Employee> employees = new ArrayList<>();
	 
	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Before
	public void start(){
		System.out.println("before");
		Employee emp = new Employee();
		emp.setFirstName("Srilatha");
		emp.setLastName("R");
		emp.setEmail("sri@gmail.com");
		employees = Collections.singletonList(emp);
		
		Mockito.when(employeeRepository.findAll())
				.thenReturn(employees);
		
		Mockito.when(employeeRepository.findById(emp.getId()))
			.thenReturn(Optional.of(emp));
		
		Mockito.when(employeeRepository.findByEmail(emp.getEmail()))
			.thenReturn(Optional.of(emp));
	}
	
	@After
	public void cleanUp(){
		System.out.println("after");
	}
	@Test
	public void FindAll() {
		List<Employee> result = employeeService.findAll();
//		Assert.assertEquals("employees should match", Collections.emptyList(), result);
		Assert.assertEquals("employees should match", employees, result);
	}

	@Test
	public void FindEmployee() {
		Employee result = employeeService.findEmployee(employees.get(0).getId());
		Assert.assertEquals("employees should match", employees.get(0), result);
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void FindEmployeeNotFound() {
		Employee result = employeeService.findEmployee("ehwdgfhdjfjh");
	}

	@Test
	public void CreateEmployee() {
		Employee em = new Employee();
		em.setFirstName("Albert");
		em.setLastName("Einstien");
		em.setEmail("albert@gmail.com");
		
		Mockito.when(employeeRepository.save(em))
			.then(invocationOnMock -> {
				em.setId("new-id"); 
				return em;
			});
		
		Employee result = employeeService.createEmployee(em);
		
		//verify that the id is not null
        Assert.assertNotNull("new emp id should exist", result.getId());
        
      //verify that the id is same as the mock id
        Assert.assertEquals("emp id should be same", "new-id", result.getId());
        		
		//verify that the repository.save() was called 1 times
        Mockito.verify(employeeRepository, Mockito.times(1))
               .save(em);
	}

	@Test(expected = BadRequestException.class)
    public void createExistingEmail() throws Exception {
		Employee em = new Employee();
		em.setFirstName("sri");
		em.setLastName("Reddy");
		em.setEmail("sri@gmail.com");
        Employee newEmp = employeeService.createEmployee(em);
    }
	
	@Test
	public void testUpdateEmployee() {
	}

	@Test
	public void testDeleteEmployee() {
	}

}
