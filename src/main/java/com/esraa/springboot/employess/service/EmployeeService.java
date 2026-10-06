package com.esraa.springboot.employess.service;

import com.esraa.springboot.employess.entity.Employee;
import com.esraa.springboot.employess.request.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {
    List<Employee>findAll();
    Employee findByID(long theId);
    Employee save(EmployeeRequest theEmployeeReq);
    Employee update(long id , EmployeeRequest employeeRequest);
    Employee convertToEmployee(long id , EmployeeRequest employeeRequest);
    void deleteById(long theId);
}
