package com.example.netty;

import com.example.netty.codec.ProtoStuffDecoder;
import com.example.netty.handler.server.ProtoStruffInboundHandler;
import com.example.netty.handler.server.ReadIdleHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * * 基于Netty实现一个服务端应用程序，在9999端口开启服务
 * *
 * * 基于Netty编写服务端程序的核心要点
 * * 1，创建boss，worker线程池，通过ServerBootstrap.group指定 线程模型
 * * 2，指定服务端Channel为NioServerSocketchannel
 * * 3，绑定端口需要sync等待绑定成功
 * * 4，channel.closeFuture.sync是为了优雅释放资源
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);
    }

    private void start(int port) {

        EventLoopGroup boss = new NioEventLoopGroup(1,new DefaultThreadFactory("boss"));
        EventLoopGroup worker = new NioEventLoopGroup(0,new DefaultThreadFactory("worker"));

        //完善线程模型
        EventExecutorGroup business = new UnorderedThreadPoolEventExecutor(NettyRuntime.availableProcessors()*2,
                new DefaultThreadFactory("business"));

        // 基于netty引导整个服务端程序的启动
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(boss, worker)
                    //配置服务端channel
                    .channel(NioServerSocketChannel.class)
                    //给服务端添加日志handler
                    .handler(new LoggingHandler(LogLevel.INFO))

                    //给childGroup添加handler
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //服务端添加read idle check，10s接收不到channel数据就断掉连接，保护自己，瘦身
                            pipeline.addLast("read idle",new ReadIdleHandler());
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4,0,4));
                            //pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new ProtoStuffDecoder());
                            pipeline.addLast(business,new ProtoStruffInboundHandler());
                            //pipeline.addLast(new StringReaderHandler());
                        }
                    });
            // 绑定端口启动
            ChannelFuture future = serverBootstrap.bind(port).sync();
            // 监听端口的关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty server error:{}", e.getMessage());
        } finally {
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
