import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 1，基于JAVA NIO API实现一个服务端应用程序，在9999端口开启服务
 * 2，服务端要求基本满足主从reactor多线程模式
 *
 * 测试 nc localhost 9999
 */
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Server server = new Server();
        server.init();
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.shutdown();
            latch.countDown();
        }));

        latch.await();
        System.out.println("server 退出");
    }
}
