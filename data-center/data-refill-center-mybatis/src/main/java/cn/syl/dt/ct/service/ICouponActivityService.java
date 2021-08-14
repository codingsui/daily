package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.CouponActivity;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface ICouponActivityService{

    /**
     * 查询流量套餐绑定的状态处于"进行中"的流量券活动
     * @return 流量券活动
     */
    CouponActivity queryByDataPackageId(Long dataPackageId);
}
