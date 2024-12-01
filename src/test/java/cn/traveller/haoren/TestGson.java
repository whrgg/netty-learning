package cn.traveller.haoren;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TestGson {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        System.out.println(gson.toJson(String.class));
    }

        static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>{
            @Override
            public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

                String json = jsonElement.getAsString();
                try {
                    return Class.forName(json);
                } catch (ClassNotFoundException e) {
                    throw new JsonParseException(e);
                }
            }

            @Override
            public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(aClass.getName());
            }
        }
}
