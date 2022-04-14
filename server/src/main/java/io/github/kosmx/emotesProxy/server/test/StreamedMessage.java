package io.github.kosmx.emotesProxy.server.test;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.server.ServerHandler;

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
