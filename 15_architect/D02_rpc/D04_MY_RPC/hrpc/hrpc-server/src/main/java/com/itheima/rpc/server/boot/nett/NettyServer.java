package com.itheima.rpc.server.boot.nett;

import com.itheima.rpc.netty.codec.FrameDecoder;
import com.itheima.rpc.netty.codec.FrameEncoder;
import com.itheima.rpc.netty.codec.RpcRequestDecoder;
import com.itheima.rpc.netty.codec.RpcResponseEncoder;
import com.itheima.rpc.netty.handler.RpcRequestHandler;
import com.itheima.rpc.server.boot.RpcServer;
import com.itheima.rpc.server.config.RpcServerConfiguration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.NonStickyEventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.nio.ch.Net;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class NettyServer implements RpcServer {

    @Autowired
    RpcServerConfiguration rpcServerConfiguration;

    @Override
    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        EventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("worker"));
        EventExecutorGroup business =
                new UnorderedThreadPoolEventExecutor(NettyRuntime.availableProcessors() * 2, new DefaultThreadFactory("business"));
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //Add encode
                            pipeline.addLast("FrameEncoder",new FrameEncoder());
                            pipeline.addLast("RpcResponseEncoder",new RpcResponseEncoder());

                            //Add decode
                            pipeline.addLast("FrameDecoder",new FrameDecoder());
                            pipeline.addLast("RpcRequestDecoder",new RpcRequestDecoder());

                            //Add business handler
                            pipeline.addLast("RequestHandler",new RpcRequestHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(rpcServerConfiguration.getRpcPort())).sync();

            future.channel().closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                    business.shutdownGracefully();
                }
            });

        } catch (InterruptedException e) {
            log.error("netty server start fail:{}", e.getMessage());
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            business.shutdownGracefully();
        }

    }
}
