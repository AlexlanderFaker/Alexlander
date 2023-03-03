package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.DishFlavor;
import com.alexlander.reggie.mapper.DishFlavorMapper;
import com.alexlander.reggie.service.DishFlavorService;
import com.alexlander.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
