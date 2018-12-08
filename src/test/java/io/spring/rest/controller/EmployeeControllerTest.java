package io.spring.rest.controller;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.spring.rest.entity.Employee;
import io.spring.rest.repository.EmployeeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest (
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private EmployeeRepository repository;
		
	@Before
	public void setUp(){
		Employee emp = new Employee();
        emp.setId("jsmith-id");
        emp.setFirstName("John");
        emp.setLastName("Smith");
        emp.setEmail("jsmith@gmail.com");
        repository.save(emp);
	}
	
	@After
	public void cleanUp(){
		repository.deleteAll();
	}
	
	@Test
	public void FinaAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("jsmith@gmail.com")));
	}

	@Test
	public void FindEmployee() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees/jsmith-id"))
        	.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("jsmith@gmail.com")));
	}
	
	@Test
	public void FindEmployee404() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/employees/ahdhdehdbsdn"))
        	.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void CreateEmployee() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Sri");
        emp.setLastName("Abbu");
        emp.setEmail("sri@gmail.com");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsBytes(emp))
                   )
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
           .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("sri@gmail.com")));


        mvc.perform(MockMvcRequestBuilders.get("/employees"))
           .andExpect(MockMvcResultMatchers.status()
                                           .isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
	}
	
	@Test
	public void CreateEmployee404() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

        Employee emp = new Employee();
        emp.setFirstName("Sri");
        emp.setLastName("Abbu");
        emp.setEmail("jsmith@gmail.com");
//        emp.setEmail("sri@gmail.com");

        mvc.perform(MockMvcRequestBuilders.post("/employees")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsBytes(emp))
                   )
           .andExpect(MockMvcResultMatchers.status()
                                           .isBadRequest());
	}

	@Test
	public void testUpdateEmployee() {
	}

	@Test
	public void testDeleteEmployee() {
	}

}
