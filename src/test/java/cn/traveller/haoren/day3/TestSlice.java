package cn.traveller.haoren.day3;

import cn.traveller.haoren.netty.day1.c3.TestByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j','k'});
        ByteBuf buf1 = buf.slice(0, 5);
        ByteBuf buf2 = buf.slice(5, 5);

        TestByteBuf.log((buf1));
        TestByteBuf.log((buf2));

        buf1.setByte(0,'b');
        System.out.println("========================================================================");


        TestByteBuf.log((buf1));
        TestByteBuf.log((buf));


    }
}
