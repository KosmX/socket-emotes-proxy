package io.github.kosmx.nettytest.client;

import io.github.kosmx.emotes.api.proxy.AbstractNetworkInstance;

import java.util.HashMap;

public class EmoteProxy extends AbstractNetworkInstance {

    ClientHandler handler;

    @Override
    public HashMap<Byte, Byte> getVersions() {
        return null;
    }

    @Override
    public void setVersions(HashMap<Byte, Byte> map) {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
