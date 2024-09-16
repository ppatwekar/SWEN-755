import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        FaultMonitorService faultMonitorService = new FaultMonitorService();

        Thread heartBeatThread = new Thread(new HeartBeatReceiver(faultMonitorService));
        heartBeatThread.start();

        //Sleeps the main thread for 12secs
        Thread.sleep(15000);
        //Shutsdown the process including heartbeatthread
        System.exit(0);
    }
}