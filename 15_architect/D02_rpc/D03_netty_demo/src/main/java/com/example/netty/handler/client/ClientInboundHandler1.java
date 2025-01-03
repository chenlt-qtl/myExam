package com.example.netty.handler.client;

import com.example.netty.bean.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ClientInboundHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} channelActive----- ", this.getClass().getSimpleName());

        for (int i = 1; i <= 100; i++) {
            ByteBuf byteBuf = ctx.alloc().buffer();
            UserInfo userInfo = new UserInfo("name"+i,i,i%2==1?"北京":"上海");
            byteBuf.writeBytes(userInfo.toString().getBytes(StandardCharsets.UTF_8));
            ctx.channel().writeAndFlush(byteBuf);
        }

        super.channelActive(ctx);
    }

    /**
     * 连续发送100次消息
     *
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
        log.info("读到服务端发来的数据 {}", new String(bytes));
        super.channelRead(ctx, msg);
    }

    /**
     * 通道内数据已读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("｛｝ exceptionCaught-----", this.getClass().getSimpleName());
        super.exceptionCaught(ctx, cause);
    }
}
