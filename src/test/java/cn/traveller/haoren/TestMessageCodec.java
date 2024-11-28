package cn.traveller.haoren;

import cn.itcast.message.LoginRequestMessage;
import cn.itcast.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        LengthFieldBasedFrameDecoder FRAME_DECODER = new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0);
        EmbeddedChannel channel =new EmbeddedChannel(
                new LoggingHandler(LogLevel.ERROR),
                FRAME_DECODER,
                new MessageCodec());
        LoginRequestMessage message =new LoginRequestMessage("zhangshan","123");
        channel.writeOutbound(message);

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);

        channel.writeInbound(buf);
    }
}
