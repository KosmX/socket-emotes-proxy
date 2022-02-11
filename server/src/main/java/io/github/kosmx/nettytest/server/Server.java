package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.coders.ProtocolEncoder;
import io.github.kosmx.nettytest.common.coders.decoder.MapConsumerDecoder;
import io.github.kosmx.nettytest.common.protocol.IMessage;
import io.github.kosmx.nettytest.common.protocol.KeepAliveMessage;
import io.github.kosmx.nettytest.server.commands.CommandHandler;
import io.github.kosmx.nettytest.server.test.TestTextMessage;
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
    private final Set<AbstractChannelHandler> connections = new CopyOnWriteArraySet<>();

    ChannelFuture channel;

    public Server(){
        init();
        run(25564);
    }

    public void init(){
        this.state = ServerState.INIT;
        protocols.put(9, TestTextMessage::new);
    }

    public void run(int port){
        protocols.put(1, KeepAliveMessage::new); //Core function
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, workers).channel(NioServerSocketChannel.class);

            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    //ch.config().setOption(ChannelOption.TCP_NODELAY, true);

                    System.out.println("Connecting client");
                    ch.pipeline().addLast(new ProtocolEncoder(), new MapConsumerDecoder(protocols), new ServerHandler());
                }
            });

            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            channel = b.bind(port).sync();//TODO port from command line
            System.out.println("Server started on port " + port);

            CommandHandler commandHandler = new CommandHandler(this);
            commandHandler.setDaemon(true); //daemons
            commandHandler.start();

            this.running();
            channel.channel().closeFuture().sync();
            System.out.println("Server is shutting down");

            commandHandler.interrupt();
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
        System.out.println("Server is now running");
    }

    public void close(){
        this.state = ServerState.STOPPING;
        connections.forEach(AbstractChannelHandler::closeConnection);
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
        connections.forEach(AbstractChannelHandler::tick);
    }
}
