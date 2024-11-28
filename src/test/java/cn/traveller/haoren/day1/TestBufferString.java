package cn.traveller.haoren.day1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.traveller.haoren.day1.ByteBufferUtil.debugAll;

public class TestBufferString {
    public static void main(String[] args) {
        //1. 字符串转化为ByteBuffer
        ByteBuffer buffer1 =ByteBuffer.allocate(16);
        buffer1.put("hello world!".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer1);

        //2.Charset ByteBuffer
        ByteBuffer buffer2 =StandardCharsets.UTF_8.encode("hello world!");
        debugAll(buffer2);

        //wrap ByteBuffer
        ByteBuffer buffer3 = ByteBuffer.wrap("hello world!".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer3);
    }
}
