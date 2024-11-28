package cn.traveller.haoren.day1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel();

        ){
            //底层使用操作系统的零拷贝优化 一次最多2G

            long size =from.size();
            for(long left=size;left>0;){
                System.out.println("position:"+(size-left)+" left:"+left);
                left-=from.transferTo((size-left),left ,to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
