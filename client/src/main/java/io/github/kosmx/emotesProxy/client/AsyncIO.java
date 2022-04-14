package io.github.kosmx.emotesProxy.client;

import io.github.kosmx.emotesProxy.client.protocol.TextMessage;

import java.util.Scanner;

public class AsyncIO extends Thread {
    private final ClientHandler client;

    public AsyncIO(ClientHandler clientHandler) {
        this.client = clientHandler;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String in = scanner.nextLine();
            TextMessage message = new TextMessage();
            message.setMsg(in);
            client.sendMessage(message);
        }
    }
}
