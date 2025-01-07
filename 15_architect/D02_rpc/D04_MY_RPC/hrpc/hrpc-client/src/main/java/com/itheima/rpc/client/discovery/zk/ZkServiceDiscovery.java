package com.itheima.rpc.client.discovery.zk;

import com.itheima.rpc.cache.ServiceProviderCache;
import com.itheima.rpc.client.discovery.RpcServiceDiscovery;
import com.itheima.rpc.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  服务发现
 *    基于zk的sdk从zk中获取数据并存入缓存
 *    设置监听-->zk中的数据发生变更后通知我,将最新的数据更新到缓存
 */
@Component
@Slf4j
public class ZkServiceDiscovery implements RpcServiceDiscovery {

    @Autowired
    private ClientZKit clientZKit;

    @Autowired
    private ServiceProviderCache providerCache;

    @Override
    public void serviceDiscovery() {

        //获得所有服务列表
        List<String> serviceList = clientZKit.getServiceList();

        serviceList.forEach(service->{
            //获取provider列表
            List<ServiceProvider> providers = clientZKit.getServiceInfos(service);
            //将服务和服务提供者存到缓存中
            providerCache.put(service,providers);

            //订阅服务提供者的变更
            clientZKit.subscribeZKEvent(service);
            log.info("订阅了{}服务，提供者列表为：{}",service,providers);

        });

    }
}
