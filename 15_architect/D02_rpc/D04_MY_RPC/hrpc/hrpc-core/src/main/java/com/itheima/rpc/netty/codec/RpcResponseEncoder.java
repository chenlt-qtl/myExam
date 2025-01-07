package com.itheima.rpc.netty.codec;

import com.itheima.rpc.data.RpcRequest;
import com.itheima.rpc.data.RpcResponse;
import com.itheima.rpc.exception.RpcException;
import com.itheima.rpc.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RpcResponseEncoder extends MessageToMessageEncoder<RpcResponse> {

    @Override
    protected void encode(ChannelHandlerContext context, RpcResponse rpcResponse, List<Object> list) throws Exception {
        try {
            byte[] bytes = ProtostuffUtil.serialize(rpcResponse);
            ByteBuf buffer = context.alloc().buffer(bytes.length);
            buffer.writeBytes(bytes);
            list.add(buffer);
        } catch (Exception e) {
            log.error("RpcResponseEncode error, msg={}", e.getMessage());
            throw new RpcException(e.getMessage());
        }
    }
}
