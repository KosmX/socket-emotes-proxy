package io.github.kosmx.emotesProxy.client;

public interface UserLogger {
    UserLogger DEFAULT = System.out::println;

    void sendMessage(String message);
}
