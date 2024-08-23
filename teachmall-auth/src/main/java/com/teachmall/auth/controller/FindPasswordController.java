package com.teachmall.auth.controller;

import com.teachmall.ucenter.model.dto.FindPasswordDto;
import com.teachmall.ucenter.service.FindPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 陈靖国
 * @Description: 找回密码
 * @Version: 1.0
 */

@Slf4j
@RestController
public class FindPasswordController {
    @Autowired
    FindPasswordService findPasswordService;
    @PostMapping("/findpassword")
    String FindPassword(@RequestBody FindPasswordDto findPasswordDto){
        findPasswordService.excute(findPasswordDto);
        return "redirect:http://www.51xuecheng.cn/sign.html?username="+findPasswordDto.getCellphone()+"&authType=wx";
    }

}
