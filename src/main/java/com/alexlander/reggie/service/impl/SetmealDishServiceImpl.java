package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.SetmealDish;
import com.alexlander.reggie.mapper.SetmealDishMapper;
import com.alexlander.reggie.mapper.SetmealMapper;
import com.alexlander.reggie.service.SetmealDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
