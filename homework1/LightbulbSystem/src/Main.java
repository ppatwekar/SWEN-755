import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread heartBeatThread = new Thread(new HeartBeatSender());
        heartBeatThread.start();

        //Sleeps the main thread for 10secs
        Thread.sleep(7000);
        //Shutsdown the process including heartbeatthread
        //this is how we should shutdown the system so that it never sends heartbeat after this
        //triggering the other system to find a fault
        System.exit(0);
    }
}