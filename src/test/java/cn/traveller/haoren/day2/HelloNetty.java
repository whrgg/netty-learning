package cn.traveller.haoren.day2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloNetty {
    public static void main(String[] args) {
         new ServerBootstrap()

                 .group(new NioEventLoopGroup())

                 .channel(NioServerSocketChannel.class)

                 .childHandler(
                     new ChannelInitializer<NioSocketChannel>() {
                     @Override
                     protected void initChannel(NioSocketChannel ch) throws Exception {
                         ch.pipeline().addLast(new StringDecoder());
                         ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                             @Override
                             public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                 System.out.println(msg);
                             }
                         });
                     }
                 }).bind(8080);
    }
}
