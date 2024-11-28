package cn.traveller.haoren;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 配置
 */
@Slf4j
public class TestConnectionTimeout {
    public static void main(String[] args) {
        //属于SocketChannal的参数
        //childoption()对子链接的配置
        //option 对父链接的配置
        //SO_TIMEOUT主要是用于阻塞IO，阻塞IO中accept等无限等待,如果不希望永远阻塞 可以用其调整时间
        NioEventLoopGroup group =new NioEventLoopGroup();
        try {

            Bootstrap bootstrap =new Bootstrap()
                    .group(group)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,300)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler());
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.debug("timeout");
        } finally {
            group.shutdownGracefully();
        }
    }
}
