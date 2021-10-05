package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.IMessage;

public class ServerHandler extends AbstractChannelHandler {
    @Override
    protected boolean handleMessage(IMessage message) {
        return false;
    }
}
