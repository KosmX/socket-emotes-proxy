package io.github.kosmx.nettytest.server.test;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.server.ServerHandler;

import java.security.InvalidParameterException;

public class StreamedMessage extends TestTextMessage {

    @Override
    public void apply(AbstractChannelHandler obj) {
        super.apply(obj);
        this.setMsg("<" + obj.toString() + ">" + this.getMsg());
        if (obj instanceof ServerHandler serverHandler) {
            var connections = serverHandler.getServer().getConnections();
            for (var connection : connections) {
                if (connection == obj) continue;

                connection.sendMessage(this);
            }
        }
        else throw new InvalidParameterException("this must be used on a server");
    }
}
