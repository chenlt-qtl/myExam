package com.itheima.rpc.server.registry.zk;

import com.itheima.rpc.annotation.HrpcService;
import com.itheima.rpc.server.config.RpcServerConfiguration;
import com.itheima.rpc.server.registry.RpcRegistry;
import com.itheima.rpc.spring.SpringBeanFactory;
import com.itheima.rpc.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@DependsOn({"springBeanFactory"})
public class ZkRegistry implements RpcRegistry {

    @Autowired
    private ServerZKit serverZKit;

    @Autowired
    private RpcServerConfiguration rpcServerConfiguration;

    @Override
    public void serviceRegistry() {
        //根据注释找到所有需要注册的服务Bean
        Map<String, Object> map = SpringBeanFactory.getBeanListByAnnotationClass(HrpcService.class);
        if (map != null && !map.isEmpty()) {
            String ip = IpUtil.getRealIp();
            //创建根节点
            serverZKit.createRootNode();
            //创建服务节点
            map.values().forEach(service->{
                //根据HrpcService中的interfaceClass属性内容进行注册
                HrpcService annotation = service.getClass().getAnnotation(HrpcService.class);
                Class<?> interfaceClass = annotation.interfaceClass();
                //使用接口全路径类名进行注册
                String serviceName = interfaceClass.getName();
                serverZKit.createPersistentNode(serviceName);

                //创建提供者节点(临时节点)
                String providerNode = ip+":"+ rpcServerConfiguration.getRpcPort();
                serverZKit.createNode(serviceName+"/"+providerNode);
                log.info("服务{}-{}完成注册",serviceName,providerNode);
            });


        }
    }
}
