package com.teachmall.orders.model.dto;

import com.teachmall.orders.model.po.XcPayRecord;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class PayRecordDto extends XcPayRecord {

    //二维码
    private String qrcode;

}
