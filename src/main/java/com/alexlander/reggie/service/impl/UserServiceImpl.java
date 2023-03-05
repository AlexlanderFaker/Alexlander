package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.User;
import com.alexlander.reggie.mapper.UserMapper;
import com.alexlander.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
