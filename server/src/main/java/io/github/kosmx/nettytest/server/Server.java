package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.coders.ProtocolEncoder;
import io.github.kosmx.nettytest.common.coders.decoder.MapConsumerDecoder;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

public final class Server {
    @Setter
    @Getter
    private ServerState state = ServerState.INIT;


    private final Map<Integer, Supplier<IMessage>> protocols = new HashMap<>();

    @Getter
    private final Set<ServerHandler> connections = new CopyOnWriteArraySet<>();


    public void run(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, workers).channel(NioServerSocketChannel.class);

            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.config().setOption(ChannelOption.TCP_NODELAY, true);

                    
                    ch.pipeline().addLast(new ProtocolEncoder(), new MapConsumerDecoder(protocols), new ServerHandler());
                }
            });

            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            b.bind(25564);//TODO port from command line

            this.state = ServerState.RUNNING;
            //TODO init command handler

        }finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }
}
