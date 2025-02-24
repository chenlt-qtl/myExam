package com.example.feign.client;

import com.example.feign.annotation.MyClient;

//模拟feign接口
@MyClient(name = "my-feign",type = "wechat")
public interface MyFeignClient{
    String feign();//将要测试的接口方法
}