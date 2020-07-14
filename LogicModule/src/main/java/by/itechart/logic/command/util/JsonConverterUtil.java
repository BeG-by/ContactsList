package by.itechart.logic.command.util;

import by.itechart.logic.entity.Contact;
import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonConverterUtil implements JsonDeserializer<Contact> {

    @Override
    public Contact deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();

        return null;
    }
}
