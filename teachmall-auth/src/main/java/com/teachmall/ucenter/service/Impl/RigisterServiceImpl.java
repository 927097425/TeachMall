package com.teachmall.ucenter.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.ucenter.mapper.XcUserMapper;
import com.teachmall.ucenter.model.dto.FindPasswordDto;
import com.teachmall.ucenter.model.dto.RegisterDto;
import com.teachmall.ucenter.model.po.XcUser;
import com.teachmall.ucenter.service.CheckCodeService;
import com.teachmall.ucenter.service.RegisterService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: 陈靖国
 * @Description: 实现类
 * @Version: 1.0
 */

@Service
public class RigisterServiceImpl implements RegisterService {

    @Autowired
    CheckCodeService.CheckCodeStore checkCodeStore;
    @Autowired
    XcUserMapper xcUserMapper;
    @Override
    public void excute(RegisterDto registerDto) {
        if(registerDto.getCellphone().isEmpty()||registerDto.getEmail().isEmpty()){
            throw new TeachmallException("请填写手机号和邮箱");
        }
        String corrctCode;

        corrctCode = checkCodeStore.get(registerDto.getCellphone());
        //判断手机号或邮箱是否在数据库
        XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getCellphone, registerDto.getCellphone()));
        if(user !=null){
            throw new TeachmallException("账号已存在");
        }

        user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getEmail, registerDto.getEmail()));
        if(user !=null){
            throw new TeachmallException("邮箱已经被绑定");
        }


        if(!registerDto.getCheckcode().equals(corrctCode)) throw new TeachmallException("验证码错误");
        if(!registerDto.getPassword().equals(registerDto.getConfirmpwd())) throw new TeachmallException("两次输入密码不一致");

        user = new XcUser();
        registerDto.setPassword(BCrypt.hashpw(registerDto.getPassword(),BCrypt.gensalt()));
        BeanUtils.copyProperties(registerDto,user);
        user.setName(registerDto.getUsername());
        user.setUtype("101001");
        user.setStatus("1");
        user.setCreateTime(LocalDateTime.now());
        xcUserMapper.insert(user);




    }
}
