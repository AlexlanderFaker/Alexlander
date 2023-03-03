package com.alexlander.reggie.service;

import com.alexlander.reggie.dto.DishDto;
import com.alexlander.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品及菜品口味信息
    public DishDto getByIdWithFlavor(Long id);
}
