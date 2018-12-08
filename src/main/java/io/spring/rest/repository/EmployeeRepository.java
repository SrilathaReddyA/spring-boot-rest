package io.spring.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import io.spring.rest.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{

	Optional<Employee> findByEmail(String email);
	List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
