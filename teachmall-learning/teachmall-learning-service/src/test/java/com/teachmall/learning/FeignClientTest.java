package com.teachmall.learning;

import com.teachmall.content.model.po.CoursePublish;
import com.teachmall.learning.feignclient.ContentServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class FeignClientTest {

    @Autowired
    ContentServiceClient contentServiceClient;


    @Test
    public void testContentServiceClient() {
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(18L);
        System.out.println(coursepublish);
        Assertions.assertNotNull(coursepublish);

    }
}