package cn.itcast.Rpc;

import cn.itcast.Rpc.service.HelloService;
import cn.itcast.message.RpcRequestMessage;
import cn.itcast.protocol.MessageCodecSharable;
import cn.itcast.protocol.ProcotolFrameDecoder;
import cn.itcast.server.handler.RpcResponseMessageHandler;
import cn.itcast.server.handler.SequenceIdGenerator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;


@Slf4j
public class RpcClientManager {

    public static Channel channel =null;
    public static final Object lock =new Object();

    public static Channel getChannel(){
        if(channel!=null){
            return channel;
        }

        synchronized (lock){
            if(channel!=null){
                return channel;
            }

            initChann();
            return channel;
        }

    }

    public static <T>  T getProxyServcie(Class<T> serviceClass){
        ClassLoader loader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};
        Object o = Proxy.newProxyInstance(loader,interfaces,(proxy, method, args) -> {
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg =new RpcRequestMessage(
                    sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );
            getChannel().writeAndFlush(msg);

            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());

            RpcResponseMessageHandler.PROMISES.put(sequenceId,promise);
//            return promise.get();
            if(promise.isSuccess()){
                return promise.getNow();
            }else{
                throw  new RuntimeException(promise.cause());
            }
        });
        return (T) o;
    }

    public static void main(String[] args) {
        HelloService service =getProxyServcie(HelloService.class);
        System.out.println(service.sayHello("zhangsan"));
        System.out.println(service.sayHello("lisi"));
    }

    private static void initChann()  {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.ERROR);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

        // rpc 响应消息处理器，待实现
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProcotolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_HANDLER);
            }
        });
        try {
            channel = bootstrap.connect("localhost", 8080).sync().channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        channel.writeAndFlush(new RpcRequestMessage(1,
               "cn.itcast.Rpc.service.HelloService",
               "sayHello",
               String.class,
               new Class[]{String.class},
               new Object[]{"张三"})).addListener(promise -> {
           if (!promise.isSuccess()) {
               Throwable cause = promise.cause();
               log.error("error", cause);
           }
       });
        try {
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (Exception e) {
            log.error("client error", e);
        }
    }
}
