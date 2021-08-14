package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface ICouponService extends IService<Coupon> {
    Coupon queryByUserAccountId(Long userAccountId);

    /**
     * 将流量券标记为已使用
     * @param id 流量券id
     */
    void markCouponUsed(Long id);

    /**
     * 插入一张流量券
     * @param coupon 流量券
     */
    void insert(Coupon coupon);
}
