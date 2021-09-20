package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof IMessage message){
            message.apply();
        } else {
            System.out.println("Error: invalid message type: " + msg.getClass());
        }
    }
}
