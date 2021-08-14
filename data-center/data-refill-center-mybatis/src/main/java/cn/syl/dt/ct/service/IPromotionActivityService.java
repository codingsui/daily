package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.PromotionActivity;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface IPromotionActivityService{
    /**
     * 查询流量套餐绑定的状态处于"进行中"的优惠活动
     * @return 优惠活动
     */
    PromotionActivity queryByDataPackageId(Long dataPackageId);
}
