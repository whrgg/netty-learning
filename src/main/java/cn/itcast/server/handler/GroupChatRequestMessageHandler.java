package cn.itcast.server.handler;

import cn.itcast.message.GroupChatRequestMessage;
import cn.itcast.message.GroupChatResponseMessage;
import cn.itcast.server.seesion.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String content = msg.getContent();
        String from = msg.getFrom();
        List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        for (Channel channel : membersChannel) {
            channel.writeAndFlush(new GroupChatResponseMessage(from,content));
        }
    }
}
