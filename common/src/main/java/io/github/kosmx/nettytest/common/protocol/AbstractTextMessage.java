package io.github.kosmx.nettytest.common.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public abstract class AbstractTextMessage implements IMessage {

    @Setter @Getter
    private String msg;

    @Override
    public int getID() {
        return 9;
    }

    @Override
    public void read(ByteBuf buf, int messageSize) throws Exception {
        byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        msg = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public void write(ByteBuf out) throws Exception {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
