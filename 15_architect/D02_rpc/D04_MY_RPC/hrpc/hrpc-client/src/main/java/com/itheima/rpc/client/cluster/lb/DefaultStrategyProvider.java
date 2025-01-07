package com.itheima.rpc.client.cluster.lb;

import com.itheima.rpc.annotation.HrpcLoadBalance;
import com.itheima.rpc.client.cluster.LoadBalanceStrategy;
import com.itheima.rpc.client.cluster.StrategyProvider;
import com.itheima.rpc.client.config.RpcClientConfiguration;
import com.itheima.rpc.exception.RpcException;
import com.itheima.rpc.spring.SpringBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DefaultStrategyProvider implements StrategyProvider {

    @Autowired
    private RpcClientConfiguration clientConfiguration;

    @Override
    public LoadBalanceStrategy getStrategy() {
        Map<String, Object> strategys = SpringBeanFactory.getBeanListByAnnotationClass(HrpcLoadBalance.class);
        String strategyStr = clientConfiguration.getRpcClientClusterStrategy();
        if (StringUtils.isNotBlank(strategyStr)) {
            for (Object strategy : strategys.values()) {
                HrpcLoadBalance annotation = strategy.getClass().getAnnotation(HrpcLoadBalance.class);
                if (strategyStr.equalsIgnoreCase(annotation.strategy())) {
                    return (LoadBalanceStrategy) strategy;
                }
            }
            throw new RpcException("strategy " + strategyStr + " is invalid");
        } else {
            return SpringBeanFactory.getBean(PollingLoadBalanceStrategy.class);
        }
    }
}
