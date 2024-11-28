package cn.itcast.server.seesion;

public class GroupSessionFactory {
    static GroupSession  groupSession = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession(){
        return groupSession;
    }
}
