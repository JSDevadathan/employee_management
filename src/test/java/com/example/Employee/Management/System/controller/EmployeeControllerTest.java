package com.example.Employee.Management.System.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.model.Employee;
import com.example.Employee.Management.System.repository.EmployeeRepository;
import com.example.Employee.Management.System.service.EmployeeService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired private EmployeeController employeeController;

    @MockBean private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ModelMapper modelMapper;

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
    public void testGetEmployeeById() {
        Long id = 1L;
        EmployeeResponse expectedResponse = new EmployeeResponse();

        Mockito.when(employeeService.getEmployeeById(id)).thenReturn(expectedResponse);

        EmployeeResponse actualResponse = employeeController.getEmployeeById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getEmployeesByDepartment() throws Exception {
        when(employeeService.getEmployeesByDepartment(Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/employees/department/{department}", "department");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
