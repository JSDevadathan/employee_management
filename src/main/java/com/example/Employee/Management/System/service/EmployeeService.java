package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.exception.EntityNotFoundException;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.save(modelMapper.map(employeeRequest, Employee.class));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Employee"));
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()) {
            throw new EntityNotFoundException("Employees");
        }
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }
}
