package cn.itcast.Rpc.service;

public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String msg) {
        int i= 1/0;
        return "hello"+msg;
    }
}
