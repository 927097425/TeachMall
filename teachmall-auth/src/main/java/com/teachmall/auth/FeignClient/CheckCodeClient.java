package com.teachmall.auth.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 陈靖国
 * @Description: 验证码远程调用
 * @Version: 1.0
 */

@FeignClient(value = "checkcode",fallbackFactory = CheckCodeClientFactory.class)

public interface CheckCodeClient {
    @PostMapping(value = "/checkcode/verify")
    public Boolean verify(@RequestParam("key") String key, @RequestParam("code")String code);
}
