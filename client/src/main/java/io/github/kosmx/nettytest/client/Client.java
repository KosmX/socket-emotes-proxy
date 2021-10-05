package io.github.kosmx.nettytest.client;

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

public class Client {


    static String host;
    static int port;

    public static void main(String[] args) {
        new ClientHandler().run(host, port);
    }

}
