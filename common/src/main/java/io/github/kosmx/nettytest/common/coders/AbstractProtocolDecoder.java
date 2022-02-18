package io.github.kosmx.nettytest.common.coders;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.AbstractByteBufferMessage;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.BufferUnderflowException;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractProtocolDecoder extends ReplayingDecoder<IMessage> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int size = in.readInt();

        if(in.readableBytes() < size) throw new BufferUnderflowException(); //return with nothing in OUT
        int messageID = in.readInt();
        IMessage message = getIMessageFromID(messageID);
        if (message == null) {
            Logger.getLogger("NetworkDecoder").warning("Receiving unknown message, ID: " + messageID);
            message = new AbstractByteBufferMessage() {
                @Override
                public void apply(AbstractChannelHandler obj) {
                    //pass
                }
            };
        }
        message.read(in, size - 4);

        out.add(message);

        //if(in.readableBytes() < 4);

    }

    protected abstract IMessage getIMessageFromID(int id);
}
