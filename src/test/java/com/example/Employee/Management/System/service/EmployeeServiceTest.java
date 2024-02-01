package com.example.Employee.Management.System.service;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testEmployees() {
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(new Employee());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any()))
                .thenReturn(EmployeeResponse.builder().id(1L).name("Name").email("email@gmail.com").department("dept").build());
        employeeService.createEmployee(new EmployeeRequest("Name", "Email", "dept"));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<EmployeeResponse>>any());
        verify(employeeRepository).save(Mockito.<Employee>any());
    }

    @Test
    void getEmployeesByDepartment(){
        when(employeeRepository.findByDepartment(Mockito.any())).thenReturn(new ArrayList<>());
        Employee employee = Employee.builder()
                .id(1L)
                .name("Name")
                .department("dept")
                .email("email@gmail.com")
                .build();

        List<EmployeeResponse> actualEmployeeByDepartment = employeeService.getEmployeesByDepartment("dept");
        verify(employeeRepository).findByDepartment(Mockito.any());
        assertTrue(actualEmployeeByDepartment.isEmpty());
    }
}
