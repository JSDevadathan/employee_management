package com.example.Employee.Management.System.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceTest {
    @MockBean private EmployeeRepository employeeRepository;

    @MockBean private ModelMapper modelMapper;

    @Autowired private EmployeeService employeeService;

    @Test
    public void testCreateEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        Employee expectedEmployee =
                Employee.builder()
                        .department(employeeRequest.getDepartment())
                        .email(employeeRequest.getEmail())
                        .name(employeeRequest.getName())
                        .build();

        EmployeeResponse expectedResponse =
                modelMapper.map(expectedEmployee, EmployeeResponse.class);

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(expectedEmployee);

        EmployeeResponse actualResponse = employeeService.createEmployee(employeeRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetEmployees() {
        Long id = 1L;
        Employee expectedEmployee = new Employee();
        EmployeeResponse expectedResponse =
                modelMapper.map(expectedEmployee, EmployeeResponse.class);
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(expectedEmployee));
        EmployeeResponse actualResponse = employeeService.getEmployees(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getEmployeesByDepartment() {
        when(employeeRepository.findByDepartment(Mockito.any())).thenReturn(new ArrayList<>());
        Employee employee =
                Employee.builder()
                        .id(1L)
                        .name("Name")
                        .department("dept")
                        .email("email@gmail.com")
                        .build();

        List<EmployeeResponse> actualEmployeeByDepartment =
                employeeService.getEmployeesByDepartment("dept");
        verify(employeeRepository).findByDepartment(Mockito.any());
        assertTrue(actualEmployeeByDepartment.isEmpty());
    }
}
