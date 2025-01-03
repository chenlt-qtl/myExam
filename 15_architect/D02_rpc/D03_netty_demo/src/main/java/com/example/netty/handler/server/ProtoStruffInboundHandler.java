package com.example.netty.handler.server;

import com.example.netty.bean.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoStruffInboundHandler extends ChannelInboundHandlerAdapter {

    private int i = 1;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} channelActive-----", this.getClass().getSimpleName());
        super.channelActive(ctx);
    }

    /**
     * 从通道读到了数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserInfo userInfo = (UserInfo) msg;
        log.info("读到客户端发来的第{}个数据 {}", i++, userInfo.toString());
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
        log.info("{} channelReadComplete-----", this.getClass().getSimpleName());
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("{} exceptionCaught-----", this.getClass().getSimpleName());
        super.exceptionCaught(ctx, cause);
    }
}
