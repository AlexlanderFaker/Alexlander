package com.alexlander.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alexlander.reggie.entity.Employee;
import com.alexlander.reggie.mapper.EmployeeMapper;
import com.alexlander.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
}
