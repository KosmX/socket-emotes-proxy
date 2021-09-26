package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.coders.ProtocolEncoder;
import io.github.kosmx.nettytest.common.coders.decoder.MapConsumerDecoder;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Supplier;

public final class Server {

    @Getter
    private ServerState state = ServerState.INIT;

    private final Timer ticker = new Timer();


    private final Map<Integer, Supplier<IMessage>> protocols = new HashMap<>();

    @Getter
    private final Set<ServerHandler> connections = new CopyOnWriteArraySet<>();

    ChannelFuture channel;

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

            channel = b.bind(25564);//TODO port from command line

            this.running();
            channel.sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }

    private void running(){
        this.state = ServerState.RUNNING;
        startTickThread();
    }

    public void close(){
        this.state = ServerState.STOPPING;
        connections.forEach(ServerHandler::closeConnection);
    }

    private void shutDown(){
        this.state = ServerState.STOPPING;

        ticker.cancel();

        channel.channel().closeFuture();
    }

    private void startTickThread(){
        ticker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 50);
    }

    private void tick(){
        if(state.getOrder() >= ServerState.CLOSING.getOrder() && connections.size() == 0){
            shutDown();
        }
        connections.forEach(ServerHandler::tick);
    }
}
