package com.example.netty.codec;

import com.example.netty.bean.UserInfo;
import com.example.netty.utils.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ProtoStuffDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        UserInfo userInfo = ProtostuffUtil.deserialize(bytes, UserInfo.class);
        list.add(userInfo);
    }
}
