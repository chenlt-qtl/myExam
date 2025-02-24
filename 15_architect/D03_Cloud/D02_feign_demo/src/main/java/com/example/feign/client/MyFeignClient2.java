package com.example.feign.client;

import com.example.feign.annotation.MyClient;

//这个不能被扫描到
@MyClient(name = "my-feign",type = "wechat")
public class MyFeignClient2 {
    void feign(){

    }//将要测试的接口方法
}