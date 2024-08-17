package com.teachmall.orders.service;

import com.teachmall.orders.model.dto.AddOrderDto;
import com.teachmall.orders.model.dto.PayRecordDto;
import com.teachmall.orders.model.dto.PayStatusDto;
import com.teachmall.orders.model.po.XcPayRecord;

/**
 * @Author: 陈靖国
 * @Description: 订单接口
 * @Version: 1.0
 */
public interface OrderService {
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto);
    public XcPayRecord getPayRecordByPayno(String payNo);
    /**
     * 请求支付宝查询支付结果
     * @param payNo 支付记录id
     * @return 支付记录信息
     */
    public PayRecordDto queryPayResult(String payNo);
    public void saveAliPayStatus(PayStatusDto payStatusDto) ;
}
