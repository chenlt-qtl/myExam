import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class BuTask implements Runnable {


    private ByteBuffer byteBuffer;
    private SocketChannel channel;

    public BuTask(SocketChannel socketChannel, ByteBuffer byteBuffer) {
        this.channel = socketChannel;
        this.byteBuffer = byteBuffer;
    }

    @Override
    public void run() {
        try {
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

        } catch (IOException e) {
            try {
                channel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
