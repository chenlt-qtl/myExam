package com.itheima.rpc.netty.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 一次解码，协议解析
 */
public class FrameEncoder extends LengthFieldPrepender {


    public FrameEncoder() {
        super(4);
    }
}
