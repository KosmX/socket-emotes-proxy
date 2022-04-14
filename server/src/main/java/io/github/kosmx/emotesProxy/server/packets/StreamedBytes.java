package io.github.kosmx.emotesProxy.server.packets;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.AbstractByteBufferMessage;
import io.github.kosmx.emotesProxy.server.ServerHandler;

import java.security.InvalidParameterException;

public class StreamedBytes extends AbstractByteBufferMessage {
    @Override
    public int getID() {
        return 16;
    }


    @Override
    public void apply(AbstractChannelHandler obj) {
        if (obj instanceof ServerHandler serverHandler) {
            var connections = serverHandler.getServer().getConnections();
            for(var connection : connections) {
                if (connection != obj) {
                    connection.sendMessage(this);
                }
            }
        }
        else {
            throw new InvalidParameterException("parameter must be a serverHandler");
        }
    }
}
