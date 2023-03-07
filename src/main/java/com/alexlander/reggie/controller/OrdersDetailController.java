package com.alexlander.reggie.controller;

import com.alexlander.reggie.common.R;
import com.alexlander.reggie.entity.Orders;
import com.alexlander.reggie.service.OrderDetailService;
import com.alexlander.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrdersDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

}
