package com.example.Employee.Management.System.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.exception.EntityNotFoundException;
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

        EmployeeResponse expectedResponse = new EmployeeResponse();

        Mockito.when(employeeService.createEmployee(employeeRequest)).thenReturn(expectedResponse);

        EmployeeResponse actualResponse = employeeService.createEmployee(employeeRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetEmployeeById() {
        Long id = 1L;
        Employee expectedEmployee = new Employee();
        EmployeeResponse expectedResponse =
                modelMapper.map(expectedEmployee, EmployeeResponse.class);
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> employeeService.getEmployeeById(id));
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(expectedEmployee));
        EmployeeResponse actualResponse = employeeService.getEmployeeById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetEmployeesByDepartment() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());
        when(employeeRepository.findByDepartment(Mockito.<String>any())).thenReturn(employeeList);
        EmployeeResponse buildResult = EmployeeResponse.builder()
                .department("Department")
                .email("js@gmail.com")
                .id(1L)
                .name("Name")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any())).thenReturn(buildResult);
        List<EmployeeResponse> actualEmployeesByDepartment = employeeService.getEmployeesByDepartment("Department");
        verify(employeeRepository).findByDepartment(Mockito.<String>any());
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any());
        assertEquals(1, actualEmployeesByDepartment.size());
    }
}
