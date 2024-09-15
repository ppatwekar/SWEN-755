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

    public HeartBeatReceiver() throws IOException {
        this.serverSocket = new ServerSocket(42975);
        System.out.println("Waiting to connect to the Lightbulb System...");
        clientSocket = serverSocket.accept();
        System.out.println("Connected to the Lightbulb system!");
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        while(true){
            try {
                String beat = bufferedReader.readLine();
                if(beat == null){
                    System.out.println("Failure Detected. Sending to Fault Monitor");
                    break;
                }
                System.out.println("Received "+beat+" from LightBulb System");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
