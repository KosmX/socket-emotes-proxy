package io.github.kosmx.nettytest.common.coders;

import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncoder extends MessageToByteEncoder<IMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, IMessage msg, ByteBuf out) throws Exception {
        int position = out.writerIndex();
        out.writeInt(0); //Just to move the pointer
        out.writeInt(msg.getID());
        msg.write(out);
        int end = out.writerIndex();
        out.writerIndex(position);
        out.writeInt(end - position - Integer.BYTES);
        out.writerIndex(end);
    }
}
