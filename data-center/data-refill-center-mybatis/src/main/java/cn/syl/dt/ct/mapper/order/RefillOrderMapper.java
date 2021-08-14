package cn.syl.dt.ct.mapper.order;

import cn.syl.dt.ct.entity.RefillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-06-30
 */
public interface RefillOrderMapper {

    @Select("select * from refill_order where user_account_id = #{1}")
    List<RefillOrder> queryAll(@Param("userAccountId")Long userAccountId);

    @Select("select * from refill_order where id = #{1}")
    RefillOrder queryById(@Param("id") Long id);

    /**
     * 增加一个充值订单
     * @param refillOrder 充值订单
     */
    @Insert("INSERT INTO refill_order("
            + "order_no,"
            + "user_account_id,"
            + "business_account_id,"
            + "business_name,"
            + "amout,"
            + "title,"
            + "type,"
            + "status,"
            + "pay_type,"
            + "refill_comment,"
            + "refill_phone_number,"
            + "refill_data,"
            + "credit"
            + ") "
            + "VALUES("
            + "#{orderNo},"
            + "#{userAccountId},"
            + "#{businessAccountId},"
            + "#{businessName},"
            + "#{amout},"
            + "#{title},"
            + "#{type},"
            + "#{status},"
            + "#{payType},"
            + "#{refillComment},"
            + "#{refillPhoneNumber},"
            + "#{refillData},"
            + "#{credit}"
            + ")")
    void save(RefillOrder refillOrder);

}
