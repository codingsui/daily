package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.RefillOrder;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface IRefillOrderService{

    /**
     * 增加一个充值订单
     * @param refillOrder 充值订单
     */
    void add(RefillOrder refillOrder);

    /**
     * 查询所有的充值订单
     * @return
     */
    List<RefillOrder> queryAll(Long userAccountId);

    /**
     * 查询充值订单
     * @param id 充值订单id
     * @return
     */
    RefillOrder queryById(Long id);
}
