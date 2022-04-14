package io.github.kosmx.emotesProxy.common.protocol;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.netty.buffer.ByteBuf;

/**
 * To keep-alive the channel
 */
public class KeepAliveMessage implements IMessage {

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void read(ByteBuf buf, int size) throws Exception {
        //what? this is empty. just the ID
    }

    @Override
    public void write(ByteBuf out) throws Exception {
        //same goes here
    }

    @Override
    public void apply(AbstractChannelHandler obj) {

    }
}
