package cn.itcast.protocol;

import cn.itcast.message.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {

    <T> T deserialize(Class<T> clazz,byte[] bytes);

    <T> byte[] serialize(T obj);

    enum Algorithm implements  Serializer{
        Java{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {

                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败",e);
                }
            }

            @Override
            public <T> byte[] serialize(T obj) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    byte[] bytes = bos.toByteArray();
                    return bytes;
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败",e);
                }
            }
        },

        Json{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes,StandardCharsets.UTF_8);
                return new Gson().fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serialize(T obj) {
                String json = new Gson().toJson(obj);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }


    }
}
