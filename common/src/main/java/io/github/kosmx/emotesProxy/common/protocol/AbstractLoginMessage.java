package io.github.kosmx.emotesProxy.common.protocol;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public abstract class AbstractLoginMessage implements IMessage {

    protected UUID userID;

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void read(ByteBuf buf, int messageSize) throws Exception {
        long a = buf.readLong();
        long b = buf.readLong();
        userID = new UUID(a, b);
    }

    @Override
    public void write(ByteBuf out) throws Exception {
        out.writeLong(userID.getMostSignificantBits());
        out.writeLong(userID.getLeastSignificantBits());
    }
}
