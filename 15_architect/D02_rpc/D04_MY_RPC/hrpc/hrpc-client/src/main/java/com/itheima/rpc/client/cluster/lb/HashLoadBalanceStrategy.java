package com.itheima.rpc.client.cluster.lb;

import com.itheima.rpc.annotation.HrpcLoadBalance;
import com.itheima.rpc.client.cluster.LoadBalanceStrategy;
import com.itheima.rpc.provider.ServiceProvider;
import com.itheima.rpc.util.IpUtil;

import java.util.List;

@HrpcLoadBalance(strategy = "hash")
public class HashLoadBalanceStrategy implements LoadBalanceStrategy {

    static String ip = IpUtil.getRealIp();
    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {

        int hashCode = ip.hashCode();
        int index = Math.abs(hashCode % serviceProviders.size());
        return serviceProviders.get(index);
    }
}
