package cn.syl.dt.ct.mapper.activity;

import cn.syl.dt.ct.entity.PromotionActivity;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-29
 */
public interface PromotionActivityMapper {
    @Select("select * from promotion_activity where data_package_id = #{1} and status = 2")
    PromotionActivity selectOne(Long dataPackageId);
}
