package com.esraa.springboot.employess.controller;

import com.esraa.springboot.employess.entity.Employee;
import com.esraa.springboot.employess.request.EmployeeRequest;
import com.esraa.springboot.employess.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Employee Rest Api Endpoint", description = "Operations related to employees ")
@RequestMapping("/api/employees")
public class EmployeeRestController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeRestController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @Operation(summary = "get  all employees.", description = "retrieve a list of all employees")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Employee>findAll(){
        return employeeService.findAll();
    }
    @Operation(summary = "fetch single employee", description = "fetch single employee from database.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable @Min(value = 1) long id ){
        Employee employee = employeeService.findByID(id);
        return employee;
    }
    @Operation(summary = "Create employee", description = "add new employee to db.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Employee addEmployee(@Valid @RequestBody EmployeeRequest theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return  dbEmployee;
    }
    @Operation(summary = "update employee", description = "update the details of current employee")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable  @Min(value=1) long id ,@Valid @RequestBody EmployeeRequest employeeRequest){
        Employee dbEmployee = employeeService.update(id , employeeRequest);
        return  dbEmployee;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete an employee", description = "delete employee by id.")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Min(value=1) long id){
        employeeService.deleteById(id);
    }
}
