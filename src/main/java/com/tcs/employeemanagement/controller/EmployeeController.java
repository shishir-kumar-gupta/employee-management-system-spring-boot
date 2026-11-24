package com.tcs.employeemanagement.controller;

import com.tcs.employeemanagement.entity.Employee;
import com.tcs.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Add Employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @Valid @RequestBody Employee employee) {

        Employee savedEmployee =
                employeeService.addEmployee(employee);

        return new ResponseEntity<>(
                savedEmployee, HttpStatus.CREATED
        );
    }

    // 2. Get All Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }

    // 3. Get Employee By ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    // 4. Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, employee)
        );
    }

    // 5. Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    // 6. Search By Department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getByDepartment(
            @PathVariable String department) {

        return ResponseEntity.ok(
                employeeService.getEmployeesByDepartment(department)
        );
    }

    // 7. Search By Name
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                employeeService.searchEmployeesByName(name)
        );
    }
}