package cn.itcast.server;

import cn.itcast.message.MessageCodecSharable;
import cn.itcast.protocol.ProcotolFrameDecoder;
import cn.itcast.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.ERROR);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoginRequestMessageHandler LOGIN_HANDLER =new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER =new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER =new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler groupJoinHandler = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler groupMembersHandler = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler groupQuitHandler = new GroupQuitRequestMessageHandler();
        GroupChatRequestMessageHandler groupChatHandler = new GroupChatRequestMessageHandler();
        QuitHandler quitHandler = new QuitHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(LOGIN_HANDLER);
                    ch.pipeline().addLast(CHAT_HANDLER);
                    ch.pipeline().addLast(GROUP_CREATE_HANDLER);
                    ch.pipeline().addLast(groupJoinHandler);
                    ch.pipeline().addLast(groupMembersHandler);
                    ch.pipeline().addLast(groupQuitHandler);
                    ch.pipeline().addLast(groupChatHandler);
                    ch.pipeline().addLast(quitHandler);
                }
            });
            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.debug("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}