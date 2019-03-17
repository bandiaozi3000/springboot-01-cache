package com.springbootcache.service;

import com.springbootcache.bean.Employee;



public interface EmployeeService {

    Employee getEmployeeById(Integer id);

    Employee  updateEmployee(Employee employee);

    void deleteEmployeeById(Integer id);
}
