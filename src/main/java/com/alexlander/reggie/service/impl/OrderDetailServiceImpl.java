package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.OrderDetail;
import com.alexlander.reggie.entity.Orders;
import com.alexlander.reggie.mapper.OrderDetailMapper;
import com.alexlander.reggie.mapper.OrdersMapper;
import com.alexlander.reggie.service.OrderDetailService;
import com.alexlander.reggie.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
