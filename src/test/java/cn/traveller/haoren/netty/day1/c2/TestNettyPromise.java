package cn.traveller.haoren.netty.day1.c2;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) {
        EventLoop eventLoop = new NioEventLoopGroup().next();

        DefaultPromise<Integer> promise =new DefaultPromise<>(eventLoop);

    }
}
