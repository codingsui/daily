package cn.syl.dt.ct.service;

import cn.syl.dt.ct.entity.Credit;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface ICreditService{

    /**
     * 增加积分
     * @param userAccountId 用户账号id
     */
    void increment(Long userAccountId, Double updatedPoint);
}
