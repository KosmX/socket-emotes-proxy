package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.github.kosmx.nettytest.common.protocol.KeepAliveMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    int lastMessage = 0;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof IMessage message){
            if(message instanceof KeepAliveMessage){
                this.lastMessage = 0;
            }
            else
            message.apply(this);
        } else {
            System.out.println("Error: invalid message type: " + msg.getClass());
        }
    }

    //ticking. 20 times every second
    public void tick(){
        if(lastMessage++ > 150){
            try {
                this.ctx.channel().closeFuture().sync();
            }catch (Exception e){
                ctx.channel().disconnect();
            }
        }
    }

    /**
     * Close EVERY connections
     */
    public void closeConnection(){
        ctx.channel().close();
    }
}
