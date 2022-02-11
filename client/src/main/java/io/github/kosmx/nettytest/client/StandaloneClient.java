package io.github.kosmx.nettytest.client;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;

import java.util.Timer;
import java.util.TimerTask;

public class StandaloneClient {

    private static final Timer ticker = new Timer();

    static String host = "localhost";
    static int port = 25564;

    public static void main(String[] args) throws InterruptedException {
        var client = new ClientHandler();
        client.run(host, port);
        startTickThread(client);
        client.waitForExit();
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
