package com.teachmall.ucenter.service.Impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teachmall.auth.FeignClient.CheckCodeClient;
import com.teachmall.ucenter.mapper.XcUserMapper;
import com.teachmall.ucenter.model.dto.AuthParamsDto;
import com.teachmall.ucenter.model.dto.XcUserExt;
import com.teachmall.ucenter.model.po.XcUser;
import com.teachmall.ucenter.service.AuthService;
import com.teachmall.ucenter.service.CheckCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: 陈靖国
 * @Description: 实现类
 * @Version: 1.0
 */

@Service("password_authservice")
public class PasswordAuthServiceImpl implements AuthService {
    @Autowired
    XcUserMapper xcUserMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CheckCodeClient checkCodeClient;

    @Override
    public XcUserExt execute(AuthParamsDto authParamsDto) {
        //账号
        String username = authParamsDto.getUsername();
        XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getUsername, username));
        if(user==null){
            //返回空表示用户不存在
            throw new RuntimeException("账号不存在");
        }
        //取出数据库存储的正确密码与输入的比对
        if (!passwordEncoder.matches(authParamsDto.getPassword(),user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }

        if(StringUtils.isEmpty(authParamsDto.getCheckcode())||StringUtils.isEmpty(authParamsDto.getCheckcodekey())){
            throw new RuntimeException("请输入验证码");
        }
        if(!checkCodeClient.verify(authParamsDto.getCheckcodekey(),authParamsDto.getCheckcode())){
            throw new RuntimeException("验证码输入错误");
        };
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(user,xcUserExt);
        return xcUserExt;
    }
}
