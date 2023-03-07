package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.ShoppingCart;
import com.alexlander.reggie.mapper.ShoppingCartMapper;
import com.alexlander.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
