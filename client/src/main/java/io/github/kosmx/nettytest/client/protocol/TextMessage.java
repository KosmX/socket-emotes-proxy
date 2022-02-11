package io.github.kosmx.nettytest.client.protocol;

import io.github.kosmx.nettytest.common.protocol.AbstractTextMessage;

public class TextMessage extends AbstractTextMessage {
    @Override
    public void apply(Object obj) {
        System.out.println(this.getMsg());
    }
}
