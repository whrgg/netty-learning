package cn.traveller.haoren.day3;

import cn.traveller.haoren.netty.day1.c3.TestByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1,2,3,4,5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{6,7,8,9,10});

        CompositeByteBuf buf = ByteBufAllocator.DEFAULT.compositeBuffer();

        buf.addComponents(true,buf1, buf2);

        TestByteBuf.log(buf);




    }
}
