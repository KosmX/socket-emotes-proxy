package io.github.kosmx.emotesProxy.client;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.coders.ProtocolEncoder;
import io.github.kosmx.emotesProxy.common.coders.decoder.MapConsumerDecoder;
import io.github.kosmx.emotesProxy.common.protocol.IMessage;
import io.github.kosmx.emotesProxy.common.protocol.KeepAliveMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@ChannelHandler.Sharable
public class ClientHandler extends AbstractChannelHandler {

    private final Map<Integer, Supplier<IMessage>> protocols = new HashMap<>();
    private ChannelFuture f;
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private int testCounter = 50;

    @Nullable
    @Getter
    @Setter
    private EmoteProxy emoteProxy;

    @Override
    protected boolean handleMessage(IMessage message) {
        return false;
    }

    /**
     * Add a protocol decoder
     * @return true if protocol has been replaced
     */
    public boolean addProtocol(int id, Supplier<IMessage> decoder) {
        return protocols.put(id, decoder) != null;
    }

    public void run(final String host, final int port) throws InterruptedException {
        protocols.put(1, KeepAliveMessage::new);
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolEncoder(), new MapConsumerDecoder(protocols), ClientHandler.this);
            }
        });
        f = b.connect(host, port).sync();
    }

    public void waitForExit() throws InterruptedException {
        this.f.channel().closeFuture().sync();
    }

    @Override
    public void tick() {
        if (!isAlive()) return;
        super.tick();//Always call it or I'm dead
        /*
        if (testCounter++ > 100) {
            var message = new TextMessage();
            message.setMsg("Hello from the client");
            System.out.println("sending hello packet");
            sendMessage(message);
            testCounter = 0;
        }
         */
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

    public boolean isAlive() {
        return (f != null) && f.channel().isOpen();
    }
}
