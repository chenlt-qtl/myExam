package com.itheima.rpc.client.proxy;

import com.itheima.rpc.client.request.RpcRequestManager;
import com.itheima.rpc.data.RpcRequest;
import com.itheima.rpc.data.RpcResponse;
import com.itheima.rpc.exception.RpcException;
import com.itheima.rpc.spring.SpringBeanFactory;
import com.itheima.rpc.util.RequestIdUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @description
 * @author: ts
 * @create:2021-05-12 00:11
 */
@Slf4j
public class CglibProxyCallBackHandler implements MethodInterceptor {


    public Object intercept(Object o, Method method, Object[] parameters, MethodProxy methodProxy) throws Throwable {
        log.info("代理调用拦截，method={}",method.getName());
        String requestId = RequestIdUtil.requestId();
        RpcRequest request = RpcRequest.builder().requestId(requestId)
                .className(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .parameters(parameters)
                .build();
        //走底层的rpc调用并获取调用结果，然后返回结果
        // 封装请求
        // 发送请求获得响应 response
        RpcRequestManager manager = SpringBeanFactory.getBean(RpcRequestManager.class);
        if(manager==null){
            throw new RpcException("spring ioc exception");
        }
        RpcResponse response = manager.sendRequest(request);

        // 返回业务结果
        return response.getResult();
    }
}
