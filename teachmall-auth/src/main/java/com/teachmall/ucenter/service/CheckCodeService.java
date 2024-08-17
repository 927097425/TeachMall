package com.teachmall.ucenter.service;



/**
 * @author Mr.M
 * @version 1.0
 * @description 验证码接口
 * @date 2022/9/29 15:59
 */
public interface CheckCodeService {



    /**
     * @description 验证码存储
     * @author Mr.M
     * @date 2022/9/29 16:34
     */
    public interface CheckCodeStore{

        /**
         * @description 向缓存设置key
         * @param key key
         * @param value value
         * @param expire 过期时间,单位秒
         * @return void
         * @author Mr.M
         * @date 2022/9/29 17:15
        */
        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}
