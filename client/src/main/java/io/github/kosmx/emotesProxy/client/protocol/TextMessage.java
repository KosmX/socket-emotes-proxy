package io.github.kosmx.emotesProxy.client.protocol;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.AbstractTextMessage;

public class TextMessage extends AbstractTextMessage {
    @Override
    public void apply(AbstractChannelHandler obj) {
        System.out.println(this.getMsg());
    }
}
