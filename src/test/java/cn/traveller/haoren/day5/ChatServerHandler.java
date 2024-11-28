package cn.traveller.haoren.day5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 创建一个 ChannelGroup，管理所有的客户端连接
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当有客户端连接时，打印一条日志
        System.out.println(ctx.channel().remoteAddress() + " 加入聊天室");
        channels.add(ctx.channel());  // 将新连接的客户端添加到 channels 集合中
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 当有客户端断开连接时，打印一条日志
        System.out.println(ctx.channel().remoteAddress() + " 离开聊天室");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 处理接收到的消息
        System.out.println(ctx.channel().remoteAddress() + " 发送消息: " + msg);

        // 向所有已连接的客户端广播消息
        for (Channel channel : channels) {
            if (channel != ctx.channel()) {  // 不广播给自己
                channel.writeAndFlush("[用户 " + ctx.channel().remoteAddress() + "] " + msg + "\n");
            } else {
                channel.writeAndFlush("[你] " + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();  // 异常发生时关闭连接
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 这里可以用来刷新或处理读取的数据
        ctx.flush();
    }
}
