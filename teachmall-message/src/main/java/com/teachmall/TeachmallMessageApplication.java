package com.teachmall;

import com.teachmall.linsener.RedisLinsener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeachmallMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeachmallMessageApplication.class, args);
    }

}
