package cn.itcast.server;

import cn.itcast.protocol.MessageCodecSharable;
import cn.itcast.protocol.ProcotolFrameDecoder;
import cn.itcast.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
                    //IdleStateHandler用来判断读写时间是否过长
                    //超过5秒则 触发IdleState#READER_IDLE事件
                    ch.pipeline().addLast(new IdleStateHandler(5,0,0));
                    //用来关注IdleStateHandler触发的事件
                    ch.pipeline().addLast(new ChannelDuplexHandler(){
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            IdleStateEvent event =(IdleStateEvent) evt;
                            //判断是否出发了读空闲事件
                            if (event.state() == IdleState.READER_IDLE) {
                                log.debug("读空闲超过5s");
                                ctx.channel().close();
                            }
                            super.userEventTriggered(ctx, evt);

//                            if(event.state() == IdleState.WRITER_IDLE){
//                                log.debug("空闲超过3s 发送一个心跳包");
//                                ctx.writeAndFlush(new PingMessage());
//                            }
                        }
                    });
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