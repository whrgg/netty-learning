package cn.traveller.haoren.day1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        try(FileChannel channel =new FileInputStream("data.txt").getChannel()){
            ByteBuffer buffer =ByteBuffer.allocate(10);
            channel.read(buffer);
            buffer.flip();
            while(buffer.hasRemaining()){
                byte b = buffer.get();
                System.out.println((char) b);
                log.debug("实际字节，{}",(char) b);
            }
            buffer.clear();
        }catch (Exception e){

        }
    }
}
