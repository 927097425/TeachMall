package com.teachmall.checkcode.service;

import com.teachmall.checkcode.model.CheckCodeParamsDto;
import com.teachmall.checkcode.model.CheckCodeResultDto;


public interface CheckCodeService {



     CheckCodeResultDto generate(CheckCodeParamsDto checkCodeParamsDto);


    public boolean verify(String key, String code);



    public interface CheckCodeGenerator{

        String generate(int length);
        

    }


    public interface KeyGenerator{


        String generate(String prefix);
    }



    public interface CheckCodeStore{


        void set(String key, String value, Integer expire);

        String get(String key);

        void remove(String key);
    }
}
