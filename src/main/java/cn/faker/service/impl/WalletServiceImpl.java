package cn.faker.service.impl;

import cn.faker.entity.Balance;
import cn.faker.entity.Flow;
import cn.faker.entity.User;
import cn.faker.mapper.BalanceMapper;
import cn.faker.mapper.FlowMapper;
import cn.faker.mapper.UserMapper;
import cn.faker.service.IWalletService;
import cn.faker.service.UserService;
import cn.faker.vo.FlowVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class WalletServiceImpl implements IWalletService {

    @Resource
    private UserService userService;
    @Resource
    private BalanceMapper balanceMapper;
    @Resource
    private FlowMapper flowMapper;

    @Override
    public Long getUserBalance(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        LambdaQueryWrapper<Balance> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Balance::getUserId, userId);
        Balance balance = balanceMapper.selectOne(queryWrapper);
        return balance.getBalance();
    }

    @Override
    public Long charge(Long userId, Long amount) {
        System.out.println("充值成功！");
        return 0L;
    }

    @Override
    public String pay(Long userId, Long amount) {
        // 查询并确认用户
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        // 尝试扣款
        // update fk_balance set balance = balance - #{amount} where user_id = #{user_id} and balance >= #{amount}
        LambdaUpdateWrapper<Balance> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.apply(" set balance = balance - {0}", amount)
                .eq(Balance::getUserId, userId)
                .ge(Balance::getBalance, amount);
        int count = balanceMapper.update(null, updateWrapper);
        if (count == 0) {
            return null;
        }
        // 记录流水
        Flow flow = new Flow();
        flow.setAmount(-amount);
        flow.setTradeTime(new Date());
        flow.setSucceed(true);
        flow.setTicket(UUID.randomUUID().toString());
        flowMapper.insert(flow);

        return flow.getTicket();
    }

    @Override
    public boolean reversal(Long userId, String token) {
        return false;
    }

    @Override
    public List<FlowVO> getUserFlow(Long userId) {
        // select * from fk_flow where user_id = #{userId}
        LambdaQueryWrapper<Flow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Flow::getUserId, userId);
        List<Flow> flowList = flowMapper.selectList(queryWrapper);
        return flowList.stream().map(flow -> {
            FlowVO vo = new FlowVO();
            vo.setAmount(flow.getAmount());
            vo.setTradeTime(flow.getTradeTime());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean refund(Long userId, Long amount) {
        // 查询并确认用户
        User user = userService.getUserById(userId);
        if (user == null) {
            return false;
        }
        // 尝试扣款
        // update fk_balance set balance = balance + #{amount} where user_id = #{userId}
        LambdaUpdateWrapper<Balance> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.apply(" set balance = balance + {0}", amount)
                .eq(Balance::getUserId, userId);
        int count = balanceMapper.update(null, updateWrapper);
        if (count == 0) {
            return false;
        }
        // 记录流水
        Flow flow = new Flow();
        flow.setAmount(amount);
        flow.setTradeTime(new Date());
        flow.setSucceed(true);
        flow.setTicket(UUID.randomUUID().toString());
        flowMapper.insert(flow);

        return true;
    }
}
