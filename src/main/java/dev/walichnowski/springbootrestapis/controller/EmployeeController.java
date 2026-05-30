package dev.walichnowski.springbootrestapis.controller;

import dev.walichnowski.springbootrestapis.entity.Employee;
import dev.walichnowski.springbootrestapis.repository.EmployeeRepository;
import dev.walichnowski.springbootrestapis.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController
{
    private final EmployeeService service;

    @GetMapping
    public List<Employee> findAll()
    {
        return service.findAll();
    }
}
