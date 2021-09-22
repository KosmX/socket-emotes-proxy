package io.github.kosmx.nettytest.common.protocol;

import io.netty.buffer.ByteBuf;

public abstract class AbstractLogoutMessage implements IMessage{
    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void read(ByteBuf buf, int messageSize) throws Exception {
        //
    }

    @Override
    public void write(ByteBuf out) throws Exception {
        //I should send disconnect reason.
    }
}
