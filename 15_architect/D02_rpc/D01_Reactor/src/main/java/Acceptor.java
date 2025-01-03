import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Acceptor extends ServerThread {

    private ServerSocketChannel serverSocketChannel;
    private Collection<PollerIO> ioThreads;

    private Iterator<PollerIO> iterator;

    public Acceptor(String name, Server server, List<PollerIO> ioThreads) {
        super(name, server);
        //不可变的list
        this.ioThreads = Collections.unmodifiableList(new ArrayList<>(ioThreads));
        iterator = ioThreads.iterator();

        try {
            serverSocketChannel = ServerSocketChannel.open();
            //config nono block
            serverSocketChannel.configureBlocking(false);

            //bind address and listen
            serverSocketChannel.bind(new InetSocketAddress(server.getPort()));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void run() {
        while (!server.isStop() && !serverSocketChannel.socket().isClosed()) {
            //查询各个channel的状态
            try {
                selector.select();
                //迭代查询结果
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    //处理完要删除 不然下次还会有
                    iterator.remove();
                    if (next.isValid() && next.isAcceptable()) {
                        doAccept(next);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        closeSelector();
    }

    private void doAccept(SelectionKey key) throws IOException {
        //取出ServerSocketChannel
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        //调用accept方法 得到socketChannel
        SocketChannel socketChannel = channel.accept();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //socketChannel交给poller
        if (!iterator.hasNext()) {
            iterator = ioThreads.iterator();
        }
        PollerIO pollerIO = iterator.next();
        pollerIO.addSockerChannel(socketChannel);
    }
}
