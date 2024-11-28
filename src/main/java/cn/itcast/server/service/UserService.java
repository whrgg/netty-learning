package cn.itcast.server.service;

/**
 * 用户管理接口
 */
public interface UserService {


    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    boolean login(String name, String password);
}
