package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.common.CustomException;
import com.alexlander.reggie.entity.Category;
import com.alexlander.reggie.entity.Dish;
import com.alexlander.reggie.entity.Employee;
import com.alexlander.reggie.entity.Setmeal;
import com.alexlander.reggie.mapper.CategoryMapper;
import com.alexlander.reggie.mapper.EmployeeMapper;
import com.alexlander.reggie.service.CategoryService;
import com.alexlander.reggie.service.DishService;
import com.alexlander.reggie.service.EmployeeService;
import com.alexlander.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联菜品
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(queryWrapper);
        if (count1 > 0) {
            //已经关联菜品，抛出异常
            throw new  CustomException("当前菜品已关联菜系，无法删除");
        }
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealQueryWrapper);
        if (count2>0){
            //已经关联套餐，抛出异常
            throw new  CustomException("当前菜品已关联套餐，无法删除");
        }
        super.removeById(id);
    }
}
