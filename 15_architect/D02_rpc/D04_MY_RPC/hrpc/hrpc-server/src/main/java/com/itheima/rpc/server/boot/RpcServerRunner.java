package com.itheima.rpc.server.boot;


import com.itheima.rpc.server.boot.nett.NettyServer;
import com.itheima.rpc.server.registry.RpcRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定成rpc server端的相关事情
 * 1,完成接口服务的注册
 *  q1:哪些接口需要对外注册?(对外暴露)
 *      答案:rpc server 框架提供注解，开发者我使用注解、标注到要对外露的接口实现上，rpc server 扫描注解，进行相关操作
 *  q2:如何注册?--->向注册中心写数据(zookeeper)
 *      答案:基于zookeeper的api完成数写入,引入zk的sdk来完成即可(curator,zkclient)
 *  q3:注册中心注册的数据结构是什么样子的?
 *      答案:参考dubbo
 * 2.基于netty编写一个服务端程序
 *  core1:编写Handler
 *      1:处理数据入找的handler:协议解析，数据反序列化，处理请求(调用业务)
 *      2:处理数据出站的handler:数据序列化,协议编码
 */

@Component
public class RpcServerRunner {

    @Autowired
    RpcRegistry rpcRegistry;

    @Autowired
    NettyServer nettyServer;

    public void run() {
        //完成接口服务的注册
        rpcRegistry.serviceRegistry();
        //基于netty编写一个服务端程序
        nettyServer.start();
    }

}
