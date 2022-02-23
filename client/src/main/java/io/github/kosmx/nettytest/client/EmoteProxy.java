package io.github.kosmx.nettytest.client;

import io.github.kosmx.emotes.api.proxy.AbstractNetworkInstance;
import io.github.kosmx.nettytest.client.protocol.ChatMessage;
import io.github.kosmx.nettytest.client.protocol.EmoteMessage;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class EmoteProxy extends AbstractNetworkInstance {

    private final ClientHandler clientHandler;
    @Setter
    @Getter
    @javax.annotation.Nullable
    private ServerData serverData = null;

    public EmoteProxy() {
        this.clientHandler = new ClientHandler();
        clientHandler.addProtocol(9, ChatMessage::new);
        clientHandler.addProtocol(16, EmoteMessage::new);
    }

    public void connect(String address, int port) throws InterruptedException {
        if (clientHandler.isAlive()) clientHandler.closeConnection();
        clientHandler.run(address, port);
    }

    ClientHandler handler;

    @Override
    public HashMap<Byte, Byte> getVersions() {
        return null;
    }

    @Override
    public void setVersions(HashMap<Byte, Byte> map) {

    }

    @Override
    public boolean sendPlayerID() {
        return true;
    }

    @Override
    protected void sendMessage(byte[] bytes, @Nullable UUID target) {
        var message = new EmoteMessage();
        message.setBuffer(bytes);
        clientHandler.sendMessage(message);
    }

    @Override
    public boolean isActive() {
        return clientHandler.isAlive();
    }

    public void disconnect() {
        this.clientHandler.closeConnection();
        this.setServerData(null);
    }

    public void tick() {
        if (clientHandler.isAlive()) {
            clientHandler.tick();
        } else {
            if (serverData != null) {
                try {
                    connect(serverData.address, serverData.port);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public record ServerData(String address, int port) {}
}
