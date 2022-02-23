package io.github.kosmx.nettytest.client;

public interface UserLogger {
    UserLogger DEFAULT = System.out::println;

    void sendMessage(String message);
}
