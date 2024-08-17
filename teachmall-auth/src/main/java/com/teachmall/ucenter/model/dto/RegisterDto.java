package com.teachmall.ucenter.model.dto;

import lombok.Data;

/**
 * @Author: 陈靖国
 * @Description: 注册所用传输类
 * @Version: 1.0
 */

@Data
public class RegisterDto extends FindPasswordDto{
    String username;
    String nickname;
}
