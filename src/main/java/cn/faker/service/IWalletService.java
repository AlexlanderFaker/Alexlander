package cn.faker.service;

import cn.faker.entity.Flow;
import cn.faker.vo.FlowVO;

import java.util.Date;
import java.util.List;

public interface IWalletService {

    /**
     * 查询钱包余额
     *
     * @param userId 用户ID
     * @return 余额（单位：分）
     */
    Long getUserBalance(Long userId);

    /**
     * 充值
     *
     * @param userId 用户ID
     * @param amount 金额（单位：分）
     * @return 余额（单位：分）
     */
    Long charge(Long userId, Long amount);

    /**
     * 支付
     *
     * @param userId 用户ID
     * @param amount 金额（单位：分）
     * @return 支付凭证
     */
    String pay(Long userId, Long amount);

    /**
     * 冲正
     *
     * @param userId 用户ID
     * @param token  支付凭证
     * @return 余额（单位：分）
     */
    boolean reversal(Long userId, String token);

    /**
     * 查询交易流水
     *
     * @param userId 用户ID
     * @return 余额（单位：分）
     */
    List<FlowVO> getUserFlow(Long userId);

    /**
     * 退款
     * @param userId 用户id
     * @param amount 退款金额
     * @return 余额
     */
    boolean refund(Long userId, Long amount);
}
