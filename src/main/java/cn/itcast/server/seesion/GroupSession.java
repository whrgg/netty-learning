package cn.itcast.server.seesion;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

public interface GroupSession {

    /**
     * 创建一个聊天组，如果不存在才创建成功,否则返回null
     * @param name
     * @param members
     * @return
     */
    cn.itcast.server.seesion.Group createGroup(String name, Set<String>members);

    /**
     * 加入聊天组
     * @param name
     * @param member
     * @return
     */
    Group joinMember(String name, String member);

    Group removeMember(String name,String member);

    Group removeGroup(String name);

    Set<String> getMembers(String name);

    List<Channel> getMembersChannel(String name);
}
