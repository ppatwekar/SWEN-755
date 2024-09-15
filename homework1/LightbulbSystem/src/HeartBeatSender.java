import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HeartBeatSender implements Runnable{


    private Socket socket;

    private PrintWriter printWriter;

    public HeartBeatSender() throws IOException {
        initializeSockets();
    }

    public void initializeSockets() throws IOException {
        socket = new Socket("localhost",42975);
        System.out.println("Connected to the LightBulb Controller!");
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {

        while(true) {
            String beat = "beat";
            try {
                printWriter.println(beat);
                printWriter.flush();

                System.out.println("Sent heartbeat to Lightbulb Controller");

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
