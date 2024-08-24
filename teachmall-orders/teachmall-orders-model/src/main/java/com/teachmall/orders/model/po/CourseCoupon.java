package com.teachmall.orders.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseCoupon {

    private long id;
    private String title;
    private String subTitle;
    private String rules;
    private String pic;
    private long payValue;
    private long actualValue;
    private short status;
    private int stock;
    private LocalDateTime createTime;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private LocalDateTime updateTime;

}
