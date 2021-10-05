package io.github.kosmx.nettytest.client;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.coders.ProtocolEncoder;
import io.github.kosmx.nettytest.common.coders.decoder.MapConsumerDecoder;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ClientHandler extends AbstractChannelHandler {

    private final Map<Integer, Supplier<IMessage>> protocols = new HashMap<>();
    private ChannelFuture f;
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();


    @Override
    protected boolean handleMessage(IMessage message) {
        return false;
    }


    public void run(final String host, final int port) {

        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolEncoder(), new MapConsumerDecoder(protocols), this);
            }
        });
        f = b.connect(host, port);
    }


    @Override
    public void closeConnection() {
        super.closeConnection();
        try {
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
