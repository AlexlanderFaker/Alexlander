package com.alexlander.reggie.controller;

import com.alexlander.reggie.common.R;
import com.alexlander.reggie.dto.SetmealDto;
import com.alexlander.reggie.entity.Category;
import com.alexlander.reggie.entity.Setmeal;
import com.alexlander.reggie.entity.SetmealDish;
import com.alexlander.reggie.service.CategoryService;
import com.alexlander.reggie.service.SetmealDishService;
import com.alexlander.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class StemealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("新增套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功！");
    }

    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list =records.stream().map((item)->{
            Long categoryId = item.getCategoryId();
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){

        // 我们需要把setmealDto返回回去，定义一个新的setmealDto用于保存数据
        SetmealDto setmealDto = new SetmealDto();

        // 将普通数据传入

        Setmeal setmeal = setmealService.getById(id);

        BeanUtils.copyProperties(setmeal,setmealDto);

        // 将菜品信息传递进去

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);

        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(list);

        // 返回setmealDto即可
        return R.success(setmealDto);
    }
}
