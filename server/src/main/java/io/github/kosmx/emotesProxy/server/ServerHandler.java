package io.github.kosmx.emotesProxy.server;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.IMessage;
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
