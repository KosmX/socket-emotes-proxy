package io.github.kosmx.nettytest.common.coders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.ByteBuffer;
import java.util.List;

public class BinaryDecoder extends ReplayingDecoder<ByteBuffer> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bytes = new byte[in.readableBytes()];
        //ByteBuffer buf = ByteBuffer.allocate(in.readInt());
        in.readBytes(bytes); //It will fail until we have the whole message. or IDK
        out.add(ByteBuffer.wrap(bytes));
    }
}
