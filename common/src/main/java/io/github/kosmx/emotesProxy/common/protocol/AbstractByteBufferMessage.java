package io.github.kosmx.emotesProxy.common.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

public abstract class AbstractByteBufferMessage implements IMessage{

    /**
     * Usually HeapByteBuffer
     */
    protected ByteBuffer buffer;

    @Override
    public int getID() {
        return 2; //2
    }

    @Override
    public void read(ByteBuf buf, int size) throws Exception {
        byte[] bytes = new byte[size];
        buf.readBytes(bytes);
        buffer = ByteBuffer.wrap(bytes);
    }

    @Override
    public void write(ByteBuf out) throws Exception {
        out.writeBytes(buffer);
    }
}
