package io.github.kosmx.nettytest.client.protocol;

import io.github.kosmx.emotes.api.proxy.INetworkInstance;
import io.github.kosmx.nettytest.client.ClientHandler;
import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.AbstractByteBufferMessage;

import java.nio.ByteBuffer;

public class EmoteMessage extends AbstractByteBufferMessage {
    @Override
    public void apply(AbstractChannelHandler obj) {
        if (obj instanceof ClientHandler clientHandler) {
            byte[] bytes = INetworkInstance.safeGetBytesFromBuffer(this.buffer);
            assert clientHandler.getEmoteProxy() != null;
            clientHandler.getEmoteProxy().receiveMessage(bytes);
        }
    }


    public void setBuffer(byte[] bytes) {
        this.buffer = ByteBuffer.wrap(bytes);
    }
}
