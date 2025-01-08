package org.company.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class JsonMessageCodec<T> implements MessageCodec<T, T> {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> typeParameterClass;

    public JsonMessageCodec(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public void encodeToWire(Buffer buffer, T object) {
        try {
            byte[] encoded = mapper.writeValueAsBytes(object);
            buffer.appendInt(encoded.length);
            buffer.appendBytes(encoded);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encode.", e);
        }
    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
        try {
            int length = buffer.getInt(pos);
            byte[] encoded = buffer.getBytes(pos + 4, pos + 4 + length);
            return mapper.readValue(encoded, typeParameterClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode.", e);
        }
    }

    @Override
    public T transform(T object) {
        return object;
    }

    @Override
    public String name() {
        return typeParameterClass.getSimpleName() + "Codec";
    }

    @Override
    public byte systemCodecID() {
        return -1; // Always -1 for user codecs.
    }
}