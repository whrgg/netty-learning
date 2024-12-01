package cn.itcast.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {
    public static Class<?> getMessageClass(int messageType){
        return MESSAGE_CLASSES.get(messageType);
    }

    public int sequenceId;
    public int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupquitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int RPC_MESSAGE_REQUSET = 14;
    public static final int RPC_MESSAGE_RESPONSE = 15;
    public static final int PING_MESSAGE = 16;
    public static final int PONG_MESSAGE = 17;


    private static final Map<Integer,Class<?>> MESSAGE_CLASSES =new HashMap<>();

    static {
        MESSAGE_CLASSES.put(LoginRequestMessage, LoginRequestMessage.class);
        MESSAGE_CLASSES.put(LoginResponseMessage, LoginResponseMessage.class);
        MESSAGE_CLASSES.put(ChatRequestMessage, ChatRequestMessage.class);
        MESSAGE_CLASSES.put(ChatResponseMessage, ChatResponseMessage.class);
        MESSAGE_CLASSES.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        MESSAGE_CLASSES.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
        MESSAGE_CLASSES.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
        MESSAGE_CLASSES.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
        MESSAGE_CLASSES.put(GroupquitRequestMessage, GroupQuitRequestMessage.class);
        MESSAGE_CLASSES.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        MESSAGE_CLASSES.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        MESSAGE_CLASSES.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
        MESSAGE_CLASSES.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
        MESSAGE_CLASSES.put(GroupMembersResponseMessage, GroupMembersResponseMessage.class);
        MESSAGE_CLASSES.put(RPC_MESSAGE_REQUSET, RpcRequestMessage.class);
        MESSAGE_CLASSES.put(RPC_MESSAGE_RESPONSE, RpcResponseMessage.class);
    }
}
