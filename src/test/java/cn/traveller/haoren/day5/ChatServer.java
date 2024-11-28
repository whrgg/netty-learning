package cn.traveller.haoren.day5;

import cn.traveller.haoren.day5.ChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ChannelPipeline;

public class ChatServer {

    private final int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        // 配置服务器的 EventLoopGroup（用于处理所有的 I/O 操作）
        EventLoopGroup bossGroup = new NioEventLoopGroup();  // 接受连接的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();  // 处理数据的线程组

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  // 采用 NIO 服务器套接字通道
                    .option(ChannelOption.SO_BACKLOG, 128)  // 设置队列大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  // 保持连接活跃
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ChatServerHandler());  // 添加自定义的处理器
                        }
                    });

            // 绑定端口，开始接收进来的连接
            Channel ch = b.bind(port).sync().channel();

            System.out.println("聊天服务器启动，监听端口 " + port);

            // 等待服务器 socket 关闭
            ch.closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new ChatServer(port).start();  // 启动服务器
    }
}
