package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.RefillOrder;
import cn.syl.dt.ct.mapper.order.RefillOrderMapper;
import cn.syl.dt.ct.service.IRefillOrderService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@DS("ct-order")
public class RefillOrderServiceImpl extends ServiceImpl<RefillOrderMapper, RefillOrder> implements IRefillOrderService {

    @Override
    public void add(RefillOrder refillOrder) {
        save(refillOrder);
    }

    @Override
    public List<RefillOrder> queryAll(Long userAccountId) {
        return list(Wrappers.<RefillOrder>lambdaQuery().eq(RefillOrder::getBusinessAccountId,userAccountId));
    }

    @Override
    public RefillOrder queryById(Long id) {
        return getById(id);
    }
}
