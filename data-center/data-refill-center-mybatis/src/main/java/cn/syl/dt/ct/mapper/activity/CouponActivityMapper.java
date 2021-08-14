package cn.syl.dt.ct.mapper.activity;

import cn.syl.dt.ct.entity.CouponActivity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface CouponActivityMapper {

    @Select("select * from coupon_activity where data_package_id = #{1} and status = 2")
    CouponActivity selectOne(@Param("id")Long dataPackageId);
}
