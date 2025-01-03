import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyFuturePromistTest {

    @Test
    public void testFuture() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        Future<String> future = group.submit(() -> {
            log.info("--异步线程执行开始--，time={}", LocalDateTime.now());

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("--异步线程执行结束--，time={}", LocalDateTime.now());
            return "hello future";
        });
        future.addListener(future1->{
            log.info("收到异步线程的通知，执行结果是：{}，时间是：{}",future1.get(),LocalDateTime.now());
        });
        log.info("--主线程--");
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testPromise() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Promise promise = new DefaultPromise(group.next());
        group.submit(()->{
            log.info("--异步线程执行开始--，time={}", LocalDateTime.now());
            try {
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess("success");
                TimeUnit.SECONDS.sleep(1);
                log.info("--异步线程执行结束--，time={}", LocalDateTime.now());
            } catch (Exception e) {
                promise.setFailure(e);
            }
        });

        promise.addListener(future1->{
            log.info("收到异步线程的通知，执行结果是：{}，时间是：{},是否成功：{}",
                    future1.get(),LocalDateTime.now(),future1.isSuccess());
        });
        log.info("--主线程--");
        TimeUnit.SECONDS.sleep(10);
    }
}
