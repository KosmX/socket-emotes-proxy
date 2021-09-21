package io.github.kosmx.nettytest.server;

import io.github.kosmx.nettytest.common.coders.BinaryDecoder;
import io.github.kosmx.nettytest.common.coders.BinaryEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main {

    //This will be a standalone program anyway
    public static void main(String[] args) {
        System.out.println("The server does NOT encrypt nor verify the clients");
        System.out.println("Do not use this to send sensitive data");
    }

}
