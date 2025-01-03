package com.example.netty;

import com.example.netty.handler.client.ClientInboundHandler1;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class NettyClient {
    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient();
        nettyClient.start("localhost",9999);
    }

    private void start(String host, int port) {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new ClientInboundHandler1());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //向服务端发送数据
            Channel channel = future.channel();
            ByteBuf byteBuf = channel.alloc().buffer();
            byteBuf.writeBytes("你好，Netty server".getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(byteBuf);

            // 监听端口的关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty client error:{}",e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}
