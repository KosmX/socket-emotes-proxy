package io.github.kosmx.nettytest.client;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;

import java.util.Timer;
import java.util.TimerTask;

public class StandaloneClient {

    private static final Timer ticker = new Timer();

    static String host;
    static int port;

    public static void main(String[] args) {
        var client = new ClientHandler();
        startTickThread(client);
        new ClientHandler().run(host, port);
        ticker.cancel();
    }


    private static void startTickThread(AbstractChannelHandler channelHandler){
        ticker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                channelHandler.tick();
            }
        }, 0, 50);
    }

}
