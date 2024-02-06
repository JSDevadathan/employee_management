package com.example.Employee.Management.System.controller;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.service.EmployeeService;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping()
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployees(@PathVariable Long id) {
        return employeeService.getEmployees(id);
    }

    @GetMapping("/department/{department}")
    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable String department) {
        return employeeService.getEmployeesByDepartment(department);
    }
}
