package com.tcs.employeemanagement.service;

import com.tcs.employeemanagement.entity.Employee;
import com.tcs.employeemanagement.repository.EmployeeRepository;
import com.tcs.employeemanagement.exception.ResourceNotFoundException;
import com.tcs.employeemanagement.exception.DuplicateResourceException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Add Employee
    public Employee addEmployee(Employee employee) {

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new DuplicateResourceException(
                    "Employee email already exists"
            );
        }

        employee.setId(null);

        return employeeRepository.save(employee);
    }

    // Get All Employees
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    // Get Employee By ID
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id
                        )
                );
    }

    // Update Employee
    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee existingEmployee = getEmployeeById(id);

        employeeRepository.findByEmail(updatedEmployee.getEmail())
                .filter(employee -> !employee.getId().equals(id))
                .ifPresent(employee -> {
                    throw new DuplicateResourceException(
                            "Email already belongs to another employee"
                    );
                });

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setJoiningDate(updatedEmployee.getJoiningDate());

        return employeeRepository.save(existingEmployee);
    }

    // Delete Employee
    public void deleteEmployee(Long id) {

        Employee employee = getEmployeeById(id);

        employeeRepository.delete(employee);
    }

    // Search By Department
    public List<Employee> getEmployeesByDepartment(String department) {

        return employeeRepository
                .findByDepartmentIgnoreCase(department);
    }

    // Search By Name
    public List<Employee> searchEmployeesByName(String name) {

        return employeeRepository
                .findByNameContainingIgnoreCase(name);
    }
}