package cn.syl.dt.ct.service.impl;

import cn.syl.dt.ct.entity.*;
import cn.syl.dt.ct.service.*;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
@Slf4j
public class DataRefillCenterServiceImpl implements DataRefillCenterService {

    @Autowired
    private IDataPackageService dataPackageService;

    @Autowired
    private ICouponService couponService;

    @Autowired
    private IAccountAmountService accountAmountService;
    @Autowired
    private ThirdPartyService thirdPartyBossService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ILotteryDrawService lotteryDrawService;
    @Autowired
    private IRefillOrderService refillOrderService;
    @Autowired
    private ICreditService creditService;

    @Override
    @Transactional(transactionManager = "xatx", rollbackFor = Exception.class)
    public RefillResponse fill(@RequestBody RefillRequest refillRequest) {
        RefillResponse refillResponse = new RefillResponse();
        refillResponse.setCode("SUCCESS");
        refillResponse.setMessage("流量充值成功");

        try {
            // 完成支付转账
            accountAmountService.transfer(refillRequest.getUserAccountId(),
                    refillRequest.getBusinessAccountId(), refillRequest.getPayAmount());
            // 创建充值订单
            refillOrderService.add(createRefillOrder(refillRequest));
            // 完成流量充值
            thirdPartyBossService.refillData(refillRequest.getPhoneNumber(),
                    refillRequest.getData());
            // 发送短信通知充值的用户
            messageService.send(refillRequest.getPhoneNumber(), "流量已经充值成功");
//            int i = 1/0;
            // 给用户增加一次抽奖机会
            lotteryDrawService.increment(refillRequest.getUserAccountId());
            // 给用户增加充值面值5%的积分
            creditService.increment(refillRequest.getUserAccountId(),
                    (double)Math.round((refillRequest.getPayAmount() * 0.05) * 100) / 100);
            // 如果使用了流量券的话，标记使用的流量券状态为已使用
            if(refillRequest.getCoupon() != null && refillRequest.getCoupon().getId() != null) {
                couponService.markCouponUsed(refillRequest.getCoupon().getId());
            }
            // 如果要赠送流量券的话，就会插入一张流量券
            CouponActivity couponActivity = refillRequest.getDataPackage().getCouponActivity();
            if(couponActivity != null && couponActivity.getId() != null) {
                couponService.insert(createCoupon(refillRequest, couponActivity));
            }
            //调用第三方充值
            thirdPartyBossService.refillData(refillRequest.getPhoneNumber(),refillRequest.getData());
        }catch (Exception e){
            log.error("异常",e);
            refillResponse.setCode("FAILURE");
            refillResponse.setMessage("充值失败");
            throw new RuntimeException("充值失败");
        }
        return refillResponse;
    }

    /**
     * 创建流量充值订单
     * @param refillRequest
     * @return
     */
    private RefillOrder createRefillOrder(RefillRequest refillRequest) {
        RefillOrder refillOrder = new RefillOrder();
        refillOrder.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        refillOrder.setUserAccountId(refillRequest.getUserAccountId());
        refillOrder.setBusinessAccountId(refillRequest.getBusinessAccountId());
        refillOrder.setBusinessName(refillRequest.getBusinessName());
        refillOrder.setAmout(refillRequest.getPayAmount());
        refillOrder.setTitle("手机流量充值");
        refillOrder.setType("通讯物流");
        refillOrder.setStatus(1);
        refillOrder.setPayType(refillRequest.getPayType());
        refillOrder.setRefillComment("给手机号码" + refillRequest.getPhoneNumber()
                + "充值" + refillRequest.getData() + "MB流量");
        refillOrder.setRefillPhoneNumber(refillRequest.getPhoneNumber());
        refillOrder.setRefillData(refillRequest.getData());
        refillOrder.setCredit((double)Math.round((refillRequest.getPayAmount() * 0.05) * 100) / 100);
        return refillOrder;
    }
    /**
     * 创建流量券实体对象
     * @param refillRequest
     * @param couponActivity
     * @return
     */
    private Coupon createCoupon(RefillRequest refillRequest,
                                CouponActivity couponActivity) {
        Coupon coupon = new Coupon();
        coupon.setUserAccountId(refillRequest.getUserAccountId());
        coupon.setCouponAmount(couponActivity.getCouponAmount());
        coupon.setStatus(1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            coupon.setEndTime(sdf.parse("2022-01-01 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return coupon;
    }
}
