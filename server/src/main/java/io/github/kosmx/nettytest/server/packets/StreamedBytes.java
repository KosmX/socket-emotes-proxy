package io.github.kosmx.nettytest.server.packets;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.AbstractByteBufferMessage;
import io.github.kosmx.nettytest.server.ServerHandler;

import java.security.InvalidParameterException;

public class StreamedBytes extends AbstractByteBufferMessage {

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
