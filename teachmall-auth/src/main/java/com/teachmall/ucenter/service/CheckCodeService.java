package com.teachmall.ucenter.service;



/**
 * @description 验证码接口
 */
public interface CheckCodeService {



    /**
     * @description 验证码存储
     */
    public interface CheckCodeStore{

        /**
         * @description 向缓存设置key
         * @param key key
         * @param value value
         * @param expire 过期时间,单位秒
         * @return void
        */
        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}
