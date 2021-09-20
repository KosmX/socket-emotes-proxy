package io.github.kosmx.nettytest.common.coders.decoder;

import io.github.kosmx.nettytest.common.protocol.IMessage;

import java.util.HashMap;
import java.util.function.Supplier;

public class MappedDecoder extends MapConsumerDecoder {

    public MappedDecoder() {
        super(new HashMap<>());
    }

    public void addDecoder(Supplier<IMessage> decoder){
        addDecoder(decoder.get().getID(), decoder);
    }

    public void addDecoder(int id, Supplier<IMessage> decoder){
        decoders.put(id, decoder);
    }

    public void removeDecoder(int id){
        decoders.remove(id);
    }

    public Supplier<IMessage> getDecoder(int id){
        return decoders.get(id);
    }
}
