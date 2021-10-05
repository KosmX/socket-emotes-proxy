package io.github.kosmx.nettytest.client;

import net.fabricmc.api.ClientModInitializer;

public class FabricLoader implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        io.github.kosmx.emotes.api.proxy.EmotesProxyManager.registerProxyInstance(new EmoteProxy());
    }

}
