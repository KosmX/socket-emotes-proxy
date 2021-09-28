package io.github.kosmx.nettytest.server.test;

import io.github.kosmx.nettytest.common.protocol.AbstractTextMessage;

public class TestTextMessage extends AbstractTextMessage {

    @Override
    public void apply(Object obj) {
        System.out.println(getMsg());
    }

}
