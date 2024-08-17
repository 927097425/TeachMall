package com.teachmall.checkcode.service.impl;

import com.teachmall.checkcode.service.CheckCodeService;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Mr.M
 * @version 1.0
 * @description 数字字母生成器
 * @date 2022/9/29 18:28
 */
@Component("NumberLetterCheckCodeGenerator")
public class NumberLetterCheckCodeGenerator implements CheckCodeService.CheckCodeGenerator {


    @Override
    public String generate(int length) {
//        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
