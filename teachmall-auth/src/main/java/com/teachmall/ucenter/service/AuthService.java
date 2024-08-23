package com.teachmall.ucenter.service;

import com.teachmall.ucenter.model.dto.AuthParamsDto;
import com.teachmall.ucenter.model.dto.XcUserExt;

public interface AuthService {

   /**
    * @description 认证方法
    * @param authParamsDto 认证参数
    * @return com.xuecheng.ucenter.model.po.XcUser 用户信息
   */
   XcUserExt execute(AuthParamsDto authParamsDto);

}