package com.alexlander.reggie.controller;

import com.alexlander.reggie.common.R;
import com.alexlander.reggie.dto.DishDto;
import com.alexlander.reggie.entity.Category;
import com.alexlander.reggie.entity.Dish;
import com.alexlander.reggie.entity.DishFlavor;
import com.alexlander.reggie.service.CategoryService;
import com.alexlander.reggie.service.DishFlavorService;
import com.alexlander.reggie.service.DishService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);

        String key = "dish_" + dishDto.getCategoryId() + "_1";//精确删除菜品分类缓存数据
        redisTemplate.delete(key);
        return R.success("添加成功！");
    }

    /**
     * 菜品分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(page, pageSize);

        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;

        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品和菜品口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 更新菜品和菜品口味
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<DishDto> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
//        Set keys = redisTemplate.keys("dish_*");//将所有菜品相关的缓存数据全部删除
//        redisTemplate.delete(keys);
        String key = "dish_" + dishDto.getCategoryId() + "_1";//精确删除菜品分类缓存数据
        redisTemplate.delete(key);
        return R.success(dishDto);
    }

    /**
     * 删除和批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        dishService.removeByIds(longs);
        return R.success("删除成功！");
    }

    /**
     * 批量处理销售状态
     *
     * @param st
     * @param ids
     * @return
     */
    @PostMapping("/status/{st}")
    public R<String> setStatus(@PathVariable int st, String ids) {
        //处理string，转成long
        String[] split = ids.split(",");
        List<Long> idList = Arrays.stream(split).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        //将每一个id   new出一个对象并设置参数
        List<Dish> dishes = idList.stream().map((item) -> {
            Dish dish = new Dish();
            dish.setId(item);
            dish.setStatus(st);
            return dish;
        }).collect(Collectors.toList());
        dishService.updateBatchById(dishes);
        return R.success("操作成功！");
    }

    /**
     * 根据条件查询菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;

        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoList != null) {
            return R.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapperDish = new LambdaQueryWrapper<>();
            queryWrapperDish.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapperDish);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }
}
