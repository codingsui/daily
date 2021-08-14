package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.AccountAmount;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface IAccountAmountService{

    /**
     * 转账
     * @param fromUserAccountId 从谁那儿转账
     * @param toUserAccountId 转到谁那儿去
     * @param amount 转账金额
     */
    void transfer(Long fromUserAccountId,
                  Long toUserAccountId, Double amount);
}
