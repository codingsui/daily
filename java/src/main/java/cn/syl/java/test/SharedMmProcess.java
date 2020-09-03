package cn.syl.java.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class SharedMmProcess {

    public static void main(String[] args) {
        new Thread(new ReadSharedMm()).start();
        new Thread(new WriteSharedMm()).start();
    }
    static class WriteSharedMm implements Runnable{
        @Override
        public void run() {
            try(RandomAccessFile raf = new RandomAccessFile("d:/swap.mm","rw")){
                FileChannel fileChannel = raf.getChannel();
                MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,1024);
                for (int i = 0; i < 1024; i++) {
                    mbb.put(i,(byte)0);
                }
                for(int i=65;i<91;i++){

                    int index = i-63;
                    int flag = mbb.get(0); //可读标置第一个字节为 0
                    if(flag != 0){ //不是可写标示 0，则重复循环，等待
                        i --;
                        continue;

                    }
                    mbb.put(0,(byte)1); //正在写数据，标志第一个字节为 1
                    mbb.put(1,(byte)(index)); //写数据的位置
                    System.out.println("程序 WriteShareMemory："+System.currentTimeMillis() +
                            "：位置：" + index +" 写入数据：" + (char)i);
                    mbb.put(index,(byte)i);//index 位置写入数据
                    mbb.put(0,(byte)2); //置可读数据标志第一个字节为 2
                    Thread.sleep(513);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ReadSharedMm implements Runnable{
        @Override
        public void run() {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile("d:/swap.mm", "rw");
                FileChannel fc = raf.getChannel();
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
                int lastIndex = 0;
                for(int i=1;i<27;i++){
                    int flag = mbb.get(0); //取读写数据的标志
                    int index = mbb.get(1); //读取数据的位置,2 为可读
                    if(flag != 2 || index == lastIndex){ //假如不可读，或未写入新数据时重复循环
                        i--;
                        continue;
                    }
                    lastIndex = index;
                    System.out.println("程序 ReadShareMemory：" + System.currentTimeMillis() +
                            "：位置：" + index +" 读出数据：" + (char)mbb.get(index));
                    mbb.put(0,(byte)0); //置第一个字节为可读标志为 0
                    if(index == 27){ //读完数据后退出
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
