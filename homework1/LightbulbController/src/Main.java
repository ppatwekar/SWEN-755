import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread heartBeatThread = new Thread(new HeartBeatReceiver());
        heartBeatThread.start();

        //Sleeps the main thread for 12secs
        Thread.sleep(10000);
        //Shutsdown the process including heartbeatthread
        System.exit(0);
    }
}