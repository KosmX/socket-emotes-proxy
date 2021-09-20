package io.github.kosmx.nettytest.common.coders;

import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public abstract class AbstractProtocolDecoder extends ReplayingDecoder<IMessage> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (true) {
            int size = in.readInt();

            if (in.readableBytes() < size) return; //return with nothing in OUT
            IMessage message = getIMessageFromID(in.readInt());
            message.read(in, size - 4);

            out.add(message);

            if(in.readableBytes() < 4) return;
        }

    }

    protected abstract IMessage getIMessageFromID(int id);
}
