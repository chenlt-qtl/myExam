package com.itheima.rpc.netty.handler;

import com.itheima.rpc.data.RpcRequest;
import com.itheima.rpc.data.RpcResponse;
import com.itheima.rpc.spring.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        //准备响应数据
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());

        try {
            //根据请求做业务
            String className = request.getClassName();
            Object bean = SpringBeanFactory.getBean(Class.forName(className));
            String methodName = request.getMethodName();
            Method method = bean.getClass().getMethod(methodName, request.getParameterTypes());
            //执行方法返回结果
            Object result = method.invoke(bean, request.getParameters());

            response.setResult(result);
        } catch (RuntimeException e) {
            response.setCause(e);
            log.error("RpcRequestHandler 执行失败，msg={} ",e.getMessage());
        } finally {
            channelHandlerContext.channel().writeAndFlush(response);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端出现异常，msg={} ",cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
