
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer {
    public static void main(String[] args) throws IOException {
        //open serverSockerChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //config nono block
        serverSocketChannel.configureBlocking(false);

        //bind address and listen
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //open selector
        Selector selector = Selector.open();

        //register serverSockerChannel to selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //loop
        while (true) {
            //查询各个channel的状态
            selector.select();
            //迭代查询结果
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //调用processSelectionKey方法
                processSelectionKey(iterator.next(), selector);
                //处理完要删除 不然下次还会有
                iterator.remove();
            }
        }
    }

    private static void processSelectionKey(SelectionKey key, Selector selector) throws IOException {
        if (key.isValid()) {
            //连接事件
            if (key.isAcceptable()) {
                //取出ServerSocketChannel
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                //调用accept方法 得到socketChannel
                SocketChannel socketChannel = channel.accept();
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                //socketChannel注册到selector中
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = channel.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String message = new String(bytes);
                    System.out.println("==== 收到消息:" + message);

                    //写数据
                    byte[] bytes1 = ("欢迎," + message + "\n").getBytes();
                    ByteBuffer byteBuffer1 = ByteBuffer.allocate(bytes1.length);
                    byteBuffer1.put(bytes1);
                    byteBuffer1.flip();
                    channel.write(byteBuffer1);
                }
            }
        }
    }
}