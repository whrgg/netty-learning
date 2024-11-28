package cn.traveller.haoren.day1.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static cn.traveller.haoren.day1.ByteBufferUtil.debugAll;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector =Selector.open();
        //1.创建服务器
        ServerSocketChannel ssc =ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //2.绑定端口

        //创建集合

        SelectionKey ssckey = ssc.register(selector, 0, null);
        //设置关注事件
        ssckey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        log.debug("register key:{}",ssckey);
        while(true){
            //3. select 方法，没有事件发生，线程阻塞，有事件 恢复运行
            selector.select();
            Iterator<SelectionKey> item = selector.selectedKeys().iterator();
            while(item.hasNext()){
                SelectionKey key = item.next();
                item.remove();
                log.debug("key: {}",key);
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc =channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, ByteBuffer.allocate(16));
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                }else if(key.isReadable()){
                    try {
                        SocketChannel channel =(SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);
                        if(read == -1){
                            key.cancel();
                        }else {
                            split(buffer);
                            if(buffer.position()==buffer.limit()){
                                ByteBuffer newBuffer =ByteBuffer.allocate(buffer.capacity()*2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++){
            if(source.get(i)=='\n'){
                int length=i+1-source.position();
                ByteBuffer target =ByteBuffer.allocate(length);
                for(int j=0; j < length;j++){
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
