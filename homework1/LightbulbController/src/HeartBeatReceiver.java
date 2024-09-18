import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class HeartBeatReceiver implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private static final int MAX_WAITING_TIME = 2000;  // Max wait time in milliseconds
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
        while (true) {
            try {
                String beat = bufferedReader.readLine();
                long currentTime = System.currentTimeMillis();

                if (detectFault(beat, currentTime)) {
                    break;  // Stop running if a fault is detected
                }

                lastUpdatedHeartBeatTimeInMills = currentTime;  // Update heartbeat time
                System.out.println("Received " + beat + " from LightBulb System");
            } catch (SocketException e) {
                System.out.println("Socket Exception: Connection reset or closed unexpectedly.");
                faultMonitorService.reportFault("SOCKET_FAULT", "Connection reset or closed unexpectedly.");
                break;  // Exit on socket fault
            }
            catch (IOException i){

            }
        }
    }

    private boolean detectFault(String beat, long currentTime) {
        // Handle null beat (Null fault)
        if (beat == null) {
            System.out.println("Null fault detected. Sending to Fault Monitor");
            faultMonitorService.reportFault("NULL_FAULT", "Heartbeat was null.");
            return true;  // Fault detected, stop the receiver
        }

        // Handle time-based fault (if heartbeat delay exceeds the maximum waiting time)
        if (currentTime - lastUpdatedHeartBeatTimeInMills > MAX_WAITING_TIME) {
            System.out.println("Time fault detected. Sending to Fault Monitor");
            faultMonitorService.reportFault("TIME_FAULT", "Heartbeat timeout exceeded " + MAX_WAITING_TIME + " milliseconds.");
            return true;  // Fault detected, stop the receiver
        }

        return false;  // No fault detected
    }
}
