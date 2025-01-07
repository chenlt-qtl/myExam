package com.itheima.rpc.client.boot;

import com.itheima.rpc.client.spring.RpcClientRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RpcClientBootstrap {

    @Autowired
    RpcClientRunner rpcClientRunner;
    @PostConstruct
    public void initRpcClient(){
        rpcClientRunner.run();
    }
}
