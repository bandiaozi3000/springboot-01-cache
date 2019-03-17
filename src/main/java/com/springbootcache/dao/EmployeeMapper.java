package com.springbootcache.dao;

import com.springbootcache.bean.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {

    Employee getEmployeeById(@Param("id")Integer id);

    void updateEmployee(Employee employee);
}
