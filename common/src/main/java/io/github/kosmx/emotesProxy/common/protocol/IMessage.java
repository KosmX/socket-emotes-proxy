package io.github.kosmx.emotesProxy.common.protocol;

import io.github.kosmx.emotesProxy.common.AbstractChannelHandler;
import io.netty.buffer.ByteBuf;

/**
 * The message encoder-decoder + handler
 */
public interface IMessage {

    /**
     * Return the protocol ID
     * @return ID
     */
    int getID();

    /**
     * Read the message from a message
     * @param buf ByteBuf
     * @param messageSize how big is the message. can be ignored in many cases
     * @throws Exception just, why not??? Seriously IDK
     */
    void read(ByteBuf buf, int messageSize) throws Exception;

    /**
     * Same here just write it
     * Size is handled by the protocol.
     * @param out write msg to this junk
     * @throws Exception :D
     */
    void write(ByteBuf out) throws Exception;

    /**
     * Called, when message is received.
     * It may put the data into a processing list, or do whatever it can. Just don't throw an {@link Exception}
     */
    void apply(AbstractChannelHandler obj);
}
