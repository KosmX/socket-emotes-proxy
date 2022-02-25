package io.github.kosmx.nettytest.client.protocol;

import io.github.kosmx.nettytest.common.AbstractChannelHandler;
import io.github.kosmx.nettytest.common.protocol.AbstractTextMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

public class ChatMessage extends AbstractTextMessage {

    @Override
    public void apply(AbstractChannelHandler obj) {
        MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText(this.getMsg()), null);
    }
}
