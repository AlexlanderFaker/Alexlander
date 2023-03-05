package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.dto.SetmealDto;
import com.alexlander.reggie.entity.Dish;
import com.alexlander.reggie.entity.Setmeal;
import com.alexlander.reggie.entity.SetmealDish;
import com.alexlander.reggie.mapper.DishMapper;
import com.alexlander.reggie.mapper.SetmealMapper;
import com.alexlander.reggie.service.DishService;
import com.alexlander.reggie.service.SetmealDishService;
import com.alexlander.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }


}
