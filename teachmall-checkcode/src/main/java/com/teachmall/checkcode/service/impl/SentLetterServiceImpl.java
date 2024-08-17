package com.teachmall.checkcode.service.impl;

import com.teachmall.checkcode.Utils.SendSms;
import com.teachmall.checkcode.model.CheckCodeDto;
import com.teachmall.checkcode.service.CheckCodeService;
import com.teachmall.checkcode.service.SentLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 陈靖国
 * @Description: 实现类
 * @Version: 1.0
 */

@Service
public class SentLetterServiceImpl implements SentLetterService {

    @Resource(name ="NumberLetterCheckCodeGenerator")
    CheckCodeService.CheckCodeGenerator checkCodeGenerator;
    @Resource(name = "RedisCheckCodeStore")
    CheckCodeService.CheckCodeStore checkCodeStore;
    @Autowired
    SendSms sendSms;
    @Override
    public void sent(String phoneNumber) throws Exception {
        CheckCodeDto checkCodeDto = new CheckCodeDto(checkCodeGenerator.generate(6));
        checkCodeStore.set(phoneNumber,checkCodeDto.getCode(),30000);
        sendSms.sendSms(phoneNumber,checkCodeDto);
    }
}
