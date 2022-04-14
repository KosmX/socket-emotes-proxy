package io.github.kosmx.emotesProxy.common;

import io.github.kosmx.emotesProxy.common.protocol.IMessage;
import io.github.kosmx.emotesProxy.common.protocol.KeepAliveMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class AbstractChannelHandler extends ChannelInboundHandlerAdapter {

    int lastMessage = 0;
    int lastKeepalive = 0;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public final void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof IMessage message) {
            if (message instanceof KeepAliveMessage) {
                this.lastMessage = 0;
            } else {
                if (!handleMessage(message)) {
                    message.apply(this);
                }
            }
        } else {
            System.out.println("Error: invalid message type: " + msg.getClass());
        }
    }

    /**
     *
     * @return true, if handled
     */
    protected abstract boolean handleMessage(IMessage message);

    //ticking. 20 times every second
    public void tick(){
        //System.out.println("test");
        if(lastMessage++ > 150){
            System.out.println("TIMEOUT: " + this.ctx);
            try {
                this.ctx.channel().close().sync();
            }catch (Exception e){
                ctx.channel().disconnect();
            }
        }

        if(lastKeepalive++ >= 20){
            sendMessage(new KeepAliveMessage());
            lastKeepalive = 0;
        }

    }

    public void sendMessage(IMessage message){
        this.ctx.writeAndFlush(message);
    }

    /**
     * Close EVERY connection
     */
    public void closeConnection(){
        if (ctx == null) return;
        ctx.channel().close();
    }
}
