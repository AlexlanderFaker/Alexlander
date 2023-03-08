package com.alexlander.reggie.controller;

import com.alexlander.reggie.common.BaseContext;
import com.alexlander.reggie.common.R;
import com.alexlander.reggie.entity.OrderDetail;
import com.alexlander.reggie.entity.Orders;
import com.alexlander.reggie.service.OrdersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    /**
     * 用户下单
     */
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("下单成功！");
    }

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Orders::getId, number);
        queryWrapper.ge(beginTime != null, Orders::getOrderTime, beginTime);
        queryWrapper.le(endTime != null, Orders::getOrderTime, endTime);
        queryWrapper.orderByDesc(Orders::getCheckoutTime);
        ordersService.page(pageInfo, queryWrapper);
        List<Orders> records = pageInfo.getRecords();
        records = records.stream().map((item) -> {
            item.setUserName("用户" + item.getUserId());
            return item;
        }).collect(Collectors.toList());
        return R.success(pageInfo);
    }

}
