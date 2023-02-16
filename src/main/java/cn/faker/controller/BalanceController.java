package cn.faker.controller;

import cn.faker.dto.Result;
import cn.faker.service.IWalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
            return Result.ok(true);
        }
        return Result.fail("支付失败！");
    }

}
