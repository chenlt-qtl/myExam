package com.example.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ServerInboundHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} channelActive-----", this.getClass().getSimpleName());
        super.channelActive(ctx);
    }

    /**
     * 从通道读到了数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{} channelRead-----", this.getClass().getSimpleName());
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        log.info("读到客户端发来的数据 {}", new String(bytes));
        super.channelRead(ctx, msg);
    }

    /**
     * 通道内数据已读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("{} channelReadComplete-----", this.getClass().getSimpleName());
        //向客户端写数据

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes("你好 Netty client".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(byteBuf);
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("{} exceptionCaught-----", this.getClass().getSimpleName());
        super.exceptionCaught(ctx, cause);
    }
}
