package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.RefillOrder;
import cn.syl.dt.ct.mapper.order.RefillOrderMapper;
import cn.syl.dt.ct.service.IRefillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
@Service

public class RefillOrderServiceImpl  implements IRefillOrderService {

    @Autowired
    private RefillOrderMapper refillOrderMapper;

    @Override
    public void add(RefillOrder refillOrder) {
        refillOrderMapper.save(refillOrder);
    }

    @Override
    public List<RefillOrder> queryAll(Long userAccountId) {
        return refillOrderMapper.queryAll(userAccountId);
    }

    @Override
    public RefillOrder queryById(Long id) {
        return refillOrderMapper.queryById(id);
    }
}
