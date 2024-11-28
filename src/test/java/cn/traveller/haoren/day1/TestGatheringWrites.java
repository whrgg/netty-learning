package cn.traveller.haoren.day1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class TestGatheringWrites {
    public static void main(String[] args) {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("Hello World!");
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("你好!");
        ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("世界!");
        try(RandomAccessFile file=new RandomAccessFile("words2.txt","rw")){
            FileChannel channel = file.getChannel();
            channel.write(new ByteBuffer[]{buffer1,buffer2,buffer3});
        }catch (IOException e){

        }
    }
}
