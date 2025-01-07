package com.itheima.rpc.client.cluster.lb;

import com.itheima.rpc.annotation.HrpcLoadBalance;
import com.itheima.rpc.client.cluster.LoadBalanceStrategy;
import com.itheima.rpc.provider.ServiceProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@HrpcLoadBalance(strategy = "polling")
public class PollingLoadBalanceStrategy implements LoadBalanceStrategy {

    private AtomicInteger nextCount = new AtomicInteger(0);
    @Override
    public ServiceProvider select(List<ServiceProvider> serviceProviders) {
        int index;
        while (true){
            index = nextCount.get();
            int next = (index++)%serviceProviders.size();
            if(nextCount.compareAndSet(index,next)){
                break;
            }
        }
        return serviceProviders.get(index);

    }
}
