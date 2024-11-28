package cn.traveller.haoren.day7;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

public class TestRedis {
    public static void main(String[] args) throws InterruptedException {
        byte[] LINE =new byte[]{13,10};
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture channelFuture = bootstrap
                .channel(NioSocketChannel.class)
                .group(worker)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buf = ctx.alloc().buffer();
                                buf.writeBytes("*3".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("$3".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("set".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("$4".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("name".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("$7".getBytes());
                                buf.writeBytes(LINE);
                                buf.writeBytes("xiaoming".getBytes());
                                buf.writeBytes(LINE);
                                ctx.writeAndFlush(buf);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).connect("localhost", 6379);

        channelFuture.channel().closeFuture().sync();
    }
}
