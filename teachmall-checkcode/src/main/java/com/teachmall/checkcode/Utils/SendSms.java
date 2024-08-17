package com.teachmall.checkcode.Utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.models.*;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.google.gson.Gson;
import com.teachmall.checkcode.model.CheckCodeDto;
import com.teachmall.checkcode.service.CheckCodeService;
import com.teachmall.checkcode.service.impl.NumberLetterCheckCodeGenerator;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

public class SendSms {


    private AsyncClient client;
    @Value("pay.accessKeyId")
    String asscessKeyId;
    @Value("pay.accessKeySecret")
    String asscessKeySecret;
    public SendSms() {
        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(asscessKeyId)
                .accessKeySecret(asscessKeySecret)
                .build());

        // Configure the Client
        this.client = AsyncClient.builder()
                .region("cn-shanghai") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
    }

    public void sendSms(String phoneNumber, CheckCodeDto code) throws Exception {
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode("SMS_154950909")
                .phoneNumbers(phoneNumber)
                .templateParam(JSON.toJSONString(code))
                .build();

        // Synchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
    }

    public void closeClient() {
        // Finally, close the client
        client.close();
    }
}
