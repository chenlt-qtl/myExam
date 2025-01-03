package com.example.netty;

import com.example.netty.handler.client.ProtoStuffInboundHandler;
import com.example.netty.codec.ProtoStuffEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

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
                            pipeline.addLast(new LengthFieldPrepender(4));

                            pipeline.addLast(new ProtoStuffEncoder());
                            pipeline.addLast(new ProtoStuffInboundHandler());
                            //pipeline.addLast(new StringEncoder());
                            //pipeline.addLast(new ClientInboundHandler1());
                            //pipeline.addLast(new StringHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 监听端口的关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty client error:{}",e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}
