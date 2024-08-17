package com.teachmall.ucenter.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 陈靖国
 * @Description: 找回密码传输数据类
 * @Version: 1.0
 */

@Data
public class FindPasswordDto {
//    {
//        cellphone:'',
//                email:'',
//            checkcodekey:'',
//            checkcode:'',
//            confirmpwd:'',
//            password:''
//    }

    String cellphone;

    String email;

    String checkcodekey;
    @NotNull
    String checkcode;

    @NotNull
    String confirmpwd;
    @NotNull
    String password;
}
