package cn.traveller.haoren.day1;

import java.nio.ByteBuffer;

import static cn.traveller.haoren.day1.ByteBufferUtil.debugAll;

public class TestByteBufferReadWirte {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        debugAll(buffer);
        buffer.put(new byte[]{0x61,0x62,0x63,0x64} );
        debugAll(buffer);
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
    }
}
