package io.github.kosmx.nettytest.common.coders.decoder;

import io.github.kosmx.nettytest.common.coders.AbstractProtocolDecoder;
import io.github.kosmx.nettytest.common.protocol.IMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MapConsumerDecoder extends AbstractProtocolDecoder {

    protected final Map<Integer, Supplier<IMessage>> decoders;

    public MapConsumerDecoder(Map<Integer, Supplier<IMessage>> decoders){
        Objects.requireNonNull(decoders);
        this.decoders = decoders;
    }

    @Override
    protected IMessage getIMessageFromID(int id) {
        try {
            return decoders.get(id).get();
        }catch (NullPointerException ignore){
            return null;
        }
    }

}
