package com.itheima.rpc.client.spring;

import com.itheima.rpc.client.discovery.RpcServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 服务发现
 */
@Component
@Slf4j
public class RpcClientRunner {

    @Autowired
    private RpcServiceDiscovery serviceDiscovery;

    public void run() {
        //服务发现和订阅
        serviceDiscovery.serviceDiscovery();

        //生成代理  基于spring后处理器

    }
}
