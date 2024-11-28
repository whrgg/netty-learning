package cn.traveller.haoren.netty.day1;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group =new NioEventLoopGroup(2 );
//        EventLoopGroup group = new DefaultEventLoop(); //普通任务 定时任务
        //获取下一个事件循环对象
        /**
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        group.next().submit(()->{
            log.debug("ok");
        });
        log.debug("main");
         **/
        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        },0,1, TimeUnit.SECONDS);

        log.debug("main");


    }
}
