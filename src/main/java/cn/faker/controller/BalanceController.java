package cn.faker.controller;

import cn.faker.dto.Result;
import cn.faker.entity.Flow;
import cn.faker.service.IWalletService;
import cn.faker.vo.FlowVO;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("/balance")
public class BalanceController {

    @Resource
    private IWalletService walletService;

    @GetMapping("/{userId}")
    public Result<Long> getUserBalance(@PathVariable("userId") Long userId) {
        Long result = walletService.getUserBalance(userId);
        return Result.ok(result);
    }

    @PostMapping("/pay")
    public Result<Boolean> pay(Long userId, Long amount) {
        String pay = walletService.pay(userId, amount);
        if (pay != null) {
            return Result.ok("支付成功");
        }
        return Result.fail("支付失败！");
    }

    @GetMapping("/flow/{userId}")
    public Result<List> getFlow(@PathVariable("userId") Long userId) {
        List<FlowVO> flow = walletService.getUserFlow(userId);
        return Result.ok(flow);
    }

    @PostMapping("/refund")
    public Result<Boolean> refund(Long userId, Long amount) {
        boolean refund = walletService.refund(userId, amount);
        if (refund != false) {
            return Result.ok("退款成功");
        }
        return Result.fail("退款失败！");
    }

}
