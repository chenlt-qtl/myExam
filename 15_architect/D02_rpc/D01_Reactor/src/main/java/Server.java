import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server {

    //监听端口 9999
    private int port = 9999;
    private Acceptor acceptor;
    private List<PollerIO> ioThreads;

    private ExecutorService buExecutor;

    // 保证线程安全
    private volatile boolean stop = false;

    public boolean isStop() {
        return stop;
    }

    public int getPort() {
        return port;
    }

    public void init() throws IOException {

        //至少4个
        int ioThreadNums = Math.max(4, Runtime.getRuntime().availableProcessors() * 2);
        ioThreads = new ArrayList<>(ioThreadNums);
        for (int i = 0; i < ioThreadNums; i++) {
            PollerIO pollerIO = new PollerIO("PollerIO " + (i + 1), this);
            ioThreads.add(pollerIO);
        }

        //初始化acceptor
        acceptor = new Acceptor("acceptor", this, ioThreads);
    }

    public void start() {
        //先启动工作线程 再启动acceptor
        buExecutor = new ThreadPoolExecutor(200, 500, 60,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));
        ioThreads.forEach(PollerIO::start);
        acceptor.start();
    }

    public void shutdown() {
        this.stop = true;
        this.buExecutor.shutdown();
    }

    public void addBuTask(BuTask buTask) {
        buExecutor.execute(buTask);
    }
}
