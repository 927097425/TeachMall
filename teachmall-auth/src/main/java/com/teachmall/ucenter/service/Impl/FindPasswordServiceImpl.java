package com.teachmall.ucenter.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.ucenter.mapper.XcUserMapper;
import com.teachmall.ucenter.model.dto.FindPasswordDto;
import com.teachmall.ucenter.model.po.XcUser;
import com.teachmall.ucenter.service.CheckCodeService;
import com.teachmall.ucenter.service.FindPasswordService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 陈靖国
 * @Description: 实现类
 * @Version: 1.0
 */

@Service
public class FindPasswordServiceImpl implements FindPasswordService {
    @Autowired
    CheckCodeService.CheckCodeStore checkCodeStore;
    @Autowired
    XcUserMapper xcUserMapper;
    @Override
    public void excute(FindPasswordDto findPasswordDto) {
        if(findPasswordDto.getCellphone().isEmpty()&&findPasswordDto.getEmail().isEmpty()){
            throw new TeachmallException("至少输入手机或者邮箱其中一个");
        }
        String corrctCode;
        if(findPasswordDto.getCellphone()!=null)
        {
            corrctCode = checkCodeStore.get(findPasswordDto.getCellphone());
            //判断手机号或邮箱是否在数据库
            XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getCellphone, findPasswordDto.getCellphone()));
            if(user ==null){
                throw new TeachmallException("账号不存在");
            }

        }
        else {
            corrctCode = checkCodeStore.get(findPasswordDto.getEmail());
            XcUser user = xcUserMapper.selectOne(new LambdaQueryWrapper<XcUser>().eq(XcUser::getEmail, findPasswordDto.getEmail()));
            if(user ==null){
                throw new TeachmallException("账号不存在");
            }
        }




        if(!findPasswordDto.getCheckcode().equals(corrctCode)) throw new TeachmallException("验证码错误");
        if(!findPasswordDto.getPassword().equals(findPasswordDto.getConfirmpwd())) throw new TeachmallException("两次输入密码不一致");


        findPasswordDto.setPassword(BCrypt.hashpw(findPasswordDto.getPassword(),BCrypt.gensalt()));
        if(findPasswordDto.getCellphone()!=null)xcUserMapper.updateByPhone(findPasswordDto);
        else xcUserMapper.updateByEmail(findPasswordDto);


    }
}
