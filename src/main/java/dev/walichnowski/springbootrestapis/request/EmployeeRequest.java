package dev.walichnowski.springbootrestapis.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequest
{
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
