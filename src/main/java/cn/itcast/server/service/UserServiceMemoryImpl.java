package cn.itcast.server.service;

import java.util.HashMap;
import java.util.Map;

public class UserServiceMemoryImpl implements UserService{
    private Map<String,String> alluserMap = new HashMap<>();
    {
        alluserMap.put("zhangsan","123");
        alluserMap.put("lisi","123");
        alluserMap.put("wangwu","123");
        alluserMap.put("zhaoliu","123");
        alluserMap.put("qangqi","123");
    }


    @Override
    public boolean login(String name, String password) {
        String pass = alluserMap.get(name);
        if(pass == null){
            return false;
        }
        return pass.equals(password);
    }
}
