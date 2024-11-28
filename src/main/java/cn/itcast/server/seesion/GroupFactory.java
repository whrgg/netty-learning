package cn.itcast.server.seesion;

public class GroupFactory {
    static GroupSession  groupSession = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession(){
        return groupSession;
    }
}
