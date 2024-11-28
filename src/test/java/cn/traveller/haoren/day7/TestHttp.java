package cn.traveller.haoren.day7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss =new NioEventLoopGroup();
        NioEventLoopGroup worker =new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap =new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss,worker);
            ChannelFuture channelFuture = serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.ERROR));
                    ch.pipeline().addLast(new HttpServerCodec());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            if (msg instanceof HttpRequest) {
                                HttpRequest request = (HttpRequest) msg;

                                // Log the request URI for debugging
                                log.debug("Request URI: {}", request.uri());

                                // Create a response
                                String responseContent = "<html><body><h1>你好，欢迎访问！</h1></body></html>";

                                // Create a ByteBuf to hold the response content
                                ByteBuf content = ctx.alloc().buffer();
                                content.writeBytes(responseContent.getBytes(Charset.forName("UTF-8")));

                                 // Build the full HTTP response
                                FullHttpResponse response = new DefaultFullHttpResponse(
                                        HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

                                // Set response headers
                                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
                                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

                                // Write and flush the response to the client
                                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                            }
                        }
                    });


                }
            }).bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
