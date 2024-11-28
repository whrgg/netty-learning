package cn.itcast.server.handler;

import cn.itcast.message.GroupCreateRequestMessage;
import cn.itcast.message.GroupCreateResponseMessage;
import cn.itcast.server.seesion.Group;
import cn.itcast.server.seesion.GroupFactory;
import cn.itcast.server.seesion.GroupSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();

        Group group = GroupFactory.getGroupSession().createGroup(groupName, members);
        if(group == null){
            ctx.writeAndFlush(new GroupCreateResponseMessage(true,groupName+"创建成功"));
        }
    }
}
