package cn.itcast.Rpc.service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String msg) {
        return "hello"+msg;
    }
}
