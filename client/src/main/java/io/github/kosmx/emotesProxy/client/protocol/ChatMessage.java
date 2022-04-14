package io.github.kosmx.emotesProxy.client.protocol;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.github.kosmx.emotesProxy.common.protocol.AbstractTextMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

public class ChatMessage extends AbstractTextMessage {

    @Override
    public void apply(AbstractChannelHandler obj) {
        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText(this.getMsg()), null);
    }
}
