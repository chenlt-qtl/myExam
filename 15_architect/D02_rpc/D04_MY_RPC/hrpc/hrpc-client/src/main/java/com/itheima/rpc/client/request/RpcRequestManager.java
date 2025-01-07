package com.itheima.rpc.client.request;

import com.itheima.rpc.cache.ServiceProviderCache;
import com.itheima.rpc.client.cluster.LoadBalanceStrategy;
import com.itheima.rpc.client.cluster.StrategyProvider;
import com.itheima.rpc.data.RpcRequest;
import com.itheima.rpc.data.RpcResponse;
import com.itheima.rpc.exception.RpcException;
import com.itheima.rpc.netty.codec.FrameDecoder;
import com.itheima.rpc.netty.codec.FrameEncoder;
import com.itheima.rpc.netty.codec.RpcRequestEncoder;
import com.itheima.rpc.netty.codec.RpcResponseDecoder;
import com.itheima.rpc.netty.handler.RpcResponseHandler;
import com.itheima.rpc.netty.request.ChannelMapping;
import com.itheima.rpc.netty.request.RequestPromise;
import com.itheima.rpc.netty.request.RpcRequestHolder;
import com.itheima.rpc.provider.ServiceProvider;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RpcRequestManager {

    @Autowired
    private ServiceProviderCache cache;

    @Autowired
    private StrategyProvider strategyProvider;

    public RpcResponse sendRequest(RpcRequest request) {
        List<ServiceProvider> providers = cache.get(request.getClassName());
        if (providers == null || providers.isEmpty()) {
            throw new RpcException("接口 " + request.getClassName() + "没有提供者");
        }
        //负载均衡
        LoadBalanceStrategy strategy = strategyProvider.getStrategy();
        ServiceProvider serviceProvider = strategy.select(providers);
        return requestByNetty(serviceProvider, request);
    }

    private RpcResponse requestByNetty(ServiceProvider provider, RpcRequest request) {
        Channel channel;
        //先有找对provider的连接是否存在，不存在则建立，存在直接使用channel
        if (!RpcRequestHolder.channelExist(provider.getServerIp(), provider.getRpcPort())) {

            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //Encode
                            pipeline.addLast("FrameEncoder", new FrameEncoder());
                            pipeline.addLast("RpcRequestEncoder", new RpcRequestEncoder());

                            //Decode
                            pipeline.addLast("FrameDecoder", new FrameDecoder());
                            pipeline.addLast("RpcResponseDecoder", new RpcResponseDecoder());

                            pipeline.addLast("RpcResponseHandler", new RpcResponseHandler());
                        }
                    });
            try {

                ChannelFuture future = bootstrap.connect(provider.getServerIp(), provider.getRpcPort()).sync();
                if (future.isSuccess()) {
                    channel = future.channel();
                    RpcRequestHolder.addChannelMapping(new ChannelMapping(provider.getServerIp(),provider.getRpcPort(),channel));
                }

            } catch (Exception e) {
                log.error("can not connect provider = {}", provider);
                throw new RpcException("can not connect provider = " + provider);
            }
        }
        try {
            //发送数据
            channel = RpcRequestHolder.getChannel(provider.getServerIp(), provider.getRpcPort());
            RequestPromise promise = new RequestPromise(channel.eventLoop());
            RpcRequestHolder.addRequestPromise(request.getRequestId(), promise);
            channel.writeAndFlush(request);

            //获取结果 如何获取异步执行的结果
            log.info("需要获取对端的结果");
            RpcResponse response = (RpcResponse) promise.get();
            RpcRequestHolder.removeRequestPromise(request.getRequestId());
            return response;

        } catch (Exception e) {
            log.error("rpc call failed, msg={}", e.getMessage());
            RpcRequestHolder.removeRequestPromise(request.getRequestId());
            throw new RpcException(e);
        }

    }
}
