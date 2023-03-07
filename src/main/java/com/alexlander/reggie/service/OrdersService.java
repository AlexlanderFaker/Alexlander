package com.alexlander.reggie.service;

import com.alexlander.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
