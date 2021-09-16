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
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, workers).channel(NioServerSocketChannel.class);
            
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new BinaryDecoder(), new BinaryEncoder(), new ServerHandler());
                }
            });

            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
        }finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }

}
