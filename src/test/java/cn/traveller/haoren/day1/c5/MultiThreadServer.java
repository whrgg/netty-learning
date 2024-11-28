package cn.traveller.haoren.day1.c5;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static cn.traveller.haoren.day1.ByteBufferUtil.debugAll;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector boss =Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress("localhost",8080));
        Worker worker =new Worker("worker-8");
        while(true){
            boss.select();
            Set<SelectionKey> selectionKeys = boss.selectedKeys();
            Iterator<SelectionKey> item = selectionKeys.iterator();
            while(item.hasNext()){
                SelectionKey key = item.next();
                item.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}",sc.getRemoteAddress());
                    log.debug("before register...{}",sc.getRemoteAddress());
                    worker.register(sc);
                    log.debug("after register...{}",sc.getRemoteAddress());
                }
            }
        }


    }
    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private volatile boolean flag = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue();

        public Worker(String name){
            this.name=name;
        }

        public void register(SocketChannel sc) throws IOException {
            if (!flag) {
                //初始化线程和selector
                thread=new Thread(this,name);
                selector =Selector.open();
                thread.start();
            }
            queue.add(()->{
                try {
                    sc.register(this.selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();
        }

        @Override
        public void run() {
            try {
                while(true){
                    selector.select();
                    Runnable task = queue.poll();
                    if(task != null){
                        task.run();
                    }
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while(iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        if(key.isReadable()){
                            SocketChannel channel = (SocketChannel)key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            channel.read(buffer);
                            buffer.flip();
                            log.debug("read {}",channel.getRemoteAddress());
                            debugAll(buffer);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

