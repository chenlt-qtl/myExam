import java.io.IOException;
import java.nio.channels.Selector;

public abstract class ServerThread extends Thread{

    protected Selector selector;
    protected Server server;

    public ServerThread(String name,Server server){
        super(name);
        this.server = server;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void closeSelector(){
        try {
            selector.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void wakeupSelector(){
        selector.wakeup();
    }
}
