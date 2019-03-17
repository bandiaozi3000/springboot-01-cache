package com.springbootcache.controller;

import com.springbootcache.bean.Employee;
import com.springbootcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    @ResponseBody
    public Employee getEmployeeById(@PathVariable("id")Integer id){
        Employee employee = employeeService.getEmployeeById(id);
        return  employee;
    }

    @GetMapping("/emp")
    @ResponseBody
    public Employee updateEmployee(Employee employee){
        employeeService.updateEmployee(employee);
        return employee;
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeService.deleteEmployeeById(id);
        return "删除成功";
    }
}
