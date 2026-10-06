package com.esraa.springboot.employess.service;

import com.esraa.springboot.employess.dao.EmployeeRepository;
import com.esraa.springboot.employess.entity.Employee;
import com.esraa.springboot.employess.request.EmployeeRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
    private EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImplementation(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByID(long theId) {
        Optional<Employee> theEmployee = employeeRepository.findById(theId);
        Employee  fetchedEmployee=null;
        if(theEmployee.isPresent()){
            fetchedEmployee=theEmployee.get();
        }else{
            throw new RuntimeException("Did not find the employee id -"+theId);
        }
        return fetchedEmployee;
    }
    @Transactional
    @Override
    public Employee save(EmployeeRequest theEmployeeReq) {
        Employee employee = convertToEmployee(0 , theEmployeeReq);
        return  employeeRepository.save(employee);
    }
    @Transactional
    @Override
    public Employee update(long id, EmployeeRequest employeeRequest) {
            Employee employee = convertToEmployee(id , employeeRequest);
            return  employeeRepository.save(employee);
    }

    @Override
    public Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        return new Employee(id , employeeRequest.getFirstName() , employeeRequest.getLastName() , employeeRequest.getEmail());
    }
    @Transactional
    @Override
    public void deleteById(long theId) {
        employeeRepository.deleteById(theId);
    }
}
