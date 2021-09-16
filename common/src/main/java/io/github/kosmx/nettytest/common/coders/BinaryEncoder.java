package io.github.kosmx.nettytest.common.coders;

import io.github.kosmx.emotes.api.proxy.INetworkInstance;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

public class BinaryEncoder extends MessageToByteEncoder<ByteBuffer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuffer msg, ByteBuf out) throws Exception {
        out.writeInt(msg.remaining());
        out.writeBytes(INetworkInstance.safeGetBytesFromBuffer(msg)); //:D we all love Java and gradle
    }


    public static byte[] buf2Bytes(ByteBuf byteBuf){
        if(byteBuf.isDirect() || byteBuf.isReadOnly()){
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            return bytes;
        }
        else {
            return byteBuf.array();
        }
    }
}
