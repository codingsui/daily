package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.Coupon;
import cn.syl.dt.ct.mapper.coupon.CouponMapper;
import cn.syl.dt.ct.service.ICouponService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
@Service
@DS("ct-coupon")
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Override
    public Coupon queryByUserAccountId(Long userAccountId) {
        return getOne(Wrappers.<Coupon>lambdaQuery().eq(Coupon::getUserAccountId,userAccountId).eq(Coupon::getStatus,1).orderByDesc(Coupon::getCouponAmount));
    }

    @Override
    public void markCouponUsed(Long id) {
        lambdaUpdate().set(Coupon::getStatus,2).eq(Coupon::getId,id).update();
    }

    @Override
    public void insert(Coupon coupon) {
        save(coupon);
    }


}
