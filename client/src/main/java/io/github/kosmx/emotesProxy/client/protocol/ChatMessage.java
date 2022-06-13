package io.github.kosmx.emotesProxy.client.protocol;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.AbstractTextMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatMessage extends AbstractTextMessage {

    @Override
    public void apply(AbstractChannelHandler obj) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(this.getMsg()));
    }
}
