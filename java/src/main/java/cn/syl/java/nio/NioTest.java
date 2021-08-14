package cn.syl.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioTest {

    public NioTest() {
    }
//    public void init(){
//        try {
//            selector = Selector.open();
//            //创建serverSocketChannel
//            ServerSocketChannel serverChannel = ServerSocketChannel.open();
//            ServerSocket socket = serverChannel.socket();
//            socket.bind(new InetSocketAddress(PORT));
//            //加入到selector中
//            serverChannel.configureBlocking(false);
//            serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SelectionKey serverKey;
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket socket = serverChannel.socket();
        socket.bind(new InetSocketAddress(8888));
        serverChannel.configureBlocking(false);
        serverKey = serverChannel.register(selector,SelectionKey.OP_ACCEPT);

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        if (selector.select() > 0){
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            while (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                if (key.isAcceptable()){
                                    iterator.remove();
                                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                                    SocketChannel socket1 = channel.accept();
                                    socket1.configureBlocking(false);
                                    socket1.register(selector,SelectionKey.OP_READ);
                                }else if (key.isReadable()){
                                    readMsg(key);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        SocketChannel socketChannel = SocketChannel.open();
                        socketChannel.connect(new InetSocketAddress("127.0.0.1",8888));
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap(String.valueOf(System.currentTimeMillis()).getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread.sleep(100000);
    }

    private static void readMsg(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int count = channel.read(byteBuffer);
            StringBuffer buf = new StringBuffer();

            if (count > 0){
                byteBuffer.flip();
                buf.append(new String(byteBuffer.array(),0,count));
            }
            System.out.println(buf.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class NioServer{

        public void init(){

        }
    }
}
