package io.github.kosmx.emotesProxy.client.protocol;

import io.github.kosmx.emotes.api.proxy.INetworkInstance;
import io.github.kosmx.emotesProxy.client.ClientHandler;
import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.AbstractByteBufferMessage;

import java.nio.ByteBuffer;

public class EmoteMessage extends AbstractByteBufferMessage {

    @Override
    public int getID() {
        return 16;
    }

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
