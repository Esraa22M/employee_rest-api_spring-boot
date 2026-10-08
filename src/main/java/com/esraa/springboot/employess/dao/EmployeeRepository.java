package com.esraa.springboot.employess.dao;

import com.esraa.springboot.employess.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   //that's it no needs to write any code.
}
