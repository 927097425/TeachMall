package com.teachmall.orders.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


 @Configuration
 public class AlipayConfig {

  //内网穿透url


  AlipayConfig(@Value("${through.url}") String throughUrl){
     AlipayConfig.notify_url = throughUrl+"/orders/";
     AlipayConfig.return_url = throughUrl+"/orders";
  }
  // 商户appid
//	public static String APPID = "";
  // 私钥 pkcs8格式的
//	public static String RSA_PRIVATE_KEY = "";
  // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  public static String notify_url = "http://6451e38f.r16.cpolar.top/orders/";
  // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
  public static String return_url = "http://6451e38f.r16.cpolar.top/orders/";
  // 请求网关地址
  public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
  // 编码
  public static String CHARSET = "UTF-8";
  // 返回格式
  public static String FORMAT = "json";
  // 支付宝公钥
//	public static String ALIPAY_PUBLIC_KEY = "";
  // 日志记录目录
  public static String log_path = "/log";
  // RSA2
  public static String SIGNTYPE = "RSA2";
 }
