package com.teachmall.checkcode.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: 陈靖国
 * @Description: 用于手机和邮箱的验证码传输类
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
public class CheckCodeDto {
    private String code;
}
