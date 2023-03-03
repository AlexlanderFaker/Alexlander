package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.dto.DishDto;
import com.alexlander.reggie.entity.Dish;
import com.alexlander.reggie.entity.DishFlavor;
import com.alexlander.reggie.mapper.DishMapper;
import com.alexlander.reggie.service.DishFlavorService;
import com.alexlander.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品同时新增菜品口味数据
     *
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品及菜品口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {

        //查询菜品信息
        Dish dish = super.getById(id);
        //查询当前菜品对应口味信息
        LambdaQueryWrapper
        return null;
    }

}
