package cn.traveller.haoren.day7;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel =new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,0,4),
                new LoggingHandler(LogLevel.ERROR)
        );


        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "hello world");
        send(buf,"Hi!");
        send(buf,"结束了啊？");
        channel.writeInbound(buf);

    }

    private static void send(ByteBuf buf, String s) {
        byte[] bytes = s.getBytes();
        int length = bytes.length;
        buf.writeInt(length);
        buf.writeBytes(bytes);
    }
}
