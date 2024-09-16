import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HeartBeatReceiver implements Runnable{

    private ServerSocket serverSocket;

    private Socket clientSocket;

    private BufferedReader bufferedReader;

    private static int MAX_WAITING_TIME = 2000;

    private long lastUpdatedHeartBeatTimeInMills;

    private FaultMonitorService faultMonitorService;

    public HeartBeatReceiver(FaultMonitorService faultMonitorService) throws IOException {
        this.serverSocket = new ServerSocket(42975);
        System.out.println("Waiting to connect to the Lightbulb System...");
        clientSocket = serverSocket.accept();
        System.out.println("Connected to the Lightbulb system!");
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        lastUpdatedHeartBeatTimeInMills = System.currentTimeMillis();
        this.faultMonitorService = faultMonitorService;
    }

    @Override
    public void run() {
        while(true){
            try {
                String beat = bufferedReader.readLine();
                long currentTime = System.currentTimeMillis();

                if(detectFault(beat,currentTime)){
                    break;
                }

                lastUpdatedHeartBeatTimeInMills = currentTime;

                System.out.println("Received "+beat+" from LightBulb System");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean detectFault(String beat, long currentTime){
        if(beat == null || (currentTime - lastUpdatedHeartBeatTimeInMills > MAX_WAITING_TIME)){
            System.out.println("Failure Detected. Sending to Fault Monitor");
            faultMonitorService.reportFault();
            return true;
        }
        return false;
    }

}
