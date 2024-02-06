package com.example.Employee.Management.System.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {
    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Email should not be blank")
    private String email;

    private String department;
}
