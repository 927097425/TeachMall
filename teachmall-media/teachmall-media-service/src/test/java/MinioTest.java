package com.teachmall.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://8.138.159.37:9000")
                    .credentials("root", "524628796")
                    .build();

   //上传文件
   @Test
   public  void upload() {
       //根据扩展名取出mimeType
       ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
       String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
       if(extensionMatch!=null){
           mimeType = extensionMatch.getMimeType();
       }
       try {
           UploadObjectArgs testbucket = UploadObjectArgs.builder()
                   .bucket("testbucket")
//                    .object("test001.mp4")
                   .object("001/training_DaL.jpeg")//添加子目录
                   .filename("C:\\Users\\Xiao Qi\\Desktop\\training_DaL.jpeg")
                   .contentType(mimeType)//默认根据扩展名确定文件内容类型，也可以指定
                   .build();
           minioClient.uploadObject(testbucket);
           System.out.println("上传成功");
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("上传失败");
       }

   }

}