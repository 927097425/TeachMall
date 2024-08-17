package com.teachmall.checkcode.service;

/**
 * @Author: 陈靖国
 * @Description: 发送短信服务
 * @Version: 1.0
 */
public interface SentLetterService {
    void sent(String phoneNumber) throws Exception;
}
