package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

public class ServerHandler extends AbstractChannelHandler {
    @Getter
    private final Server server;

    public ServerHandler(Server server) {
        this.server = server;
    }

    @Override
    protected boolean handleMessage(IMessage message) {
        return false;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Disconnected: " + ctx.channel());
        super.channelUnregistered(ctx);
        server.getConnections().remove(this);
    }
}
