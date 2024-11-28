package cn.traveller.haoren.day1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static cn.traveller.haoren.day1.ByteBufferUtil.debugAll;

public class TestScatteringReads {
    public static void main(String[] args) {
        try(RandomAccessFile file=new RandomAccessFile("3parts.txt","r")){
            FileChannel channel =file.getChannel();
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(3);
            ByteBuffer buffer3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{buffer1,buffer2,buffer3});
            debugAll(buffer1);
            debugAll(buffer2);
            debugAll(buffer3);
        }catch (IOException e){

        }
    }
}
