package com.example.Employee.Management.System.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.Employee.Management.System.contract.request.EmployeeRequest;
import com.example.Employee.Management.System.contract.response.EmployeeResponse;
import com.example.Employee.Management.System.service.EmployeeService;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    @Test
    public void testCreateEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest();

        EmployeeResponse expectedResponse = new EmployeeResponse();

        Mockito.when(employeeService.createEmployee(employeeRequest)).thenReturn(expectedResponse);

        EmployeeResponse actualResponse = employeeController.createEmployee(employeeRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetEmployees() {
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
