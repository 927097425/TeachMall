package com.teachmall.ucenter.service;

import com.teachmall.ucenter.model.po.XcUser;

public interface WxAuthService {

    public XcUser wxAuth(String code);

}