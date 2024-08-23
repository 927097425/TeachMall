package com.teachmall.auth.controller;

import com.teachmall.ucenter.model.dto.FindPasswordDto;
import com.teachmall.ucenter.model.dto.RegisterDto;
import com.teachmall.ucenter.service.FindPasswordService;
import com.teachmall.ucenter.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 陈靖国
 * @Description: 注册
 * @Version: 1.0
 */

@Slf4j
@RestController
public class RigistController {
    @Autowired
    RegisterService registerService;
    @PostMapping("/register")
    String regist(@RequestBody RegisterDto registerDto){
        registerService.excute(registerDto);
        return "redirect:http://www.51xuecheng.cn/sign.html?";

    }
}
