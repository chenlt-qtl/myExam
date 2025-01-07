package com.example.netty.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 服务端添加read idle check，10s接收不到channel数据就断掉连接，保护自己，瘦身
 */
@Slf4j
public class ReadIdleHandler extends IdleStateHandler {

    public ReadIdleHandler() {
        super(10, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {

        if(evt.equals(IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT)){
            //read idle
            log.info("read idle---");
            ctx.close();
        }
        super.channelIdle(ctx, evt);
    }
}
