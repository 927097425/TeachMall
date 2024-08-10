package com.teachmall;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class TeachmallContentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmallContentApiApplication.class, args);
    }

}
