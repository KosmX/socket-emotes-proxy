package io.github.kosmx.nettytest.server.test;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.AbstractTextMessage;
import io.netty.buffer.ByteBuf;

public class TestTextMessage extends AbstractTextMessage {

    @Override
    public void read(ByteBuf buf, int messageSize) throws Exception {
        //System.out.println(messageSize);
        super.read(buf, messageSize);
    }

    @Override
    public void apply(AbstractChannelHandler obj) {
        System.out.println("Receiving message:");
        System.out.println(getMsg());
    }

}
