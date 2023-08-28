package com.qing.erp.system.ws.pojo;

import com.google.gson.*;
import com.qing.erp.system.ws.constant.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.HashMap;

@NoArgsConstructor
@Data
public class MessageEntity {
    private MessageType type;
    private String msg;
    private HashMap<String, Object> data = new HashMap<>();

    public MessageEntity(MessageType type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static MessageEntity fallback() {
        return new MessageEntity(MessageType.NORMAL_MESSAGE, "success");
    }

    public static MessageEntity build(MessageType type) {
        return new MessageEntity(type, "success");
    }

    public static MessageEntity build(MessageType type, String msg) {
        return new MessageEntity(type, msg);
    }

    public void addData(String k, Object v) {
        this.data.put(k, v);
    }


    // 用来解决gson无法将json中的type读取到实体类的枚举属性
    public static class MessageTypeAdapter implements JsonSerializer<MessageType>, JsonDeserializer<MessageType> {

        @Override
        public MessageType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return MessageType.fromValue(jsonElement.getAsInt());
        }

        @Override
        public JsonElement serialize(MessageType messageType, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(messageType.name().toLowerCase());
        }
    }
}

