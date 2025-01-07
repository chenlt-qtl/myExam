package com.example.netty.handler.client;

import com.example.netty.bean.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 *
 * 客户端添加write idle check+keepalive, 5s不发送数据就发送一个keepalive，避免连接被断，也避免频繁
 * keepalive
 */
public class WriteIdleHandler extends IdleStateHandler {
    public WriteIdleHandler() {
        super(0, 5, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if(evt.equals(IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT)){
            UserInfo userInfo = new UserInfo("心跳" , 0, "");
            ctx.channel().writeAndFlush(userInfo);
        }
        super.channelIdle(ctx, evt);
    }
}
