package com.example.netty.codec;

import com.example.netty.bean.UserInfo;
import com.example.netty.utils.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class ProtoStuffEncoder extends MessageToMessageEncoder<UserInfo> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, UserInfo userInfo, List<Object> list) throws Exception {
        byte[] bytes = ProtostuffUtil.serialize(userInfo);
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(bytes.length);
        byteBuf.writeBytes(bytes);
        list.add(byteBuf);
    }
}
