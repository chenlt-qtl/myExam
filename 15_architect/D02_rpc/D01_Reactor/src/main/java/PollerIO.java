import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class PollerIO extends ServerThread {

    private Queue<SocketChannel> queue;

    public PollerIO(String name, Server server) {
        super(name, server);
        queue = new LinkedBlockingQueue<>();

    }

    public void addSockerChannel(SocketChannel socketChannel) {
        queue.offer(socketChannel);
        wakeupSelector();
    }

    @Override
    public void run() {
        while (!server.isStop()) {
            doSelect();
            doAcceptChannel();
        }
        closeSelector();
    }

    private void doAcceptChannel() {
        SocketChannel socketChannel;
        while (!server.isStop() && (socketChannel = queue.poll()) != null) {

            //socketChannel注册到selector中
            try {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void doSelect() {
        try {
            selector.select();
            //迭代查询结果
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //处理完要删除 不然下次还会有
                iterator.remove();
                if (key.isValid() && key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = channel.read(byteBuffer);
                    if (read > 0) {
                        server.addBuTask(new BuTask(channel,byteBuffer));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
