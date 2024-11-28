package cn.itcast.server.seesion;

public class SessionFactory {

    static Session session =new SessionMemoryImpl();

    public static Session getSession(){
        return session;
    }
}
