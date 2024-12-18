package PortInAndOut;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SinglePortDataOut implements Runnable{
    private ConcurrentLinkedQueue<String> dataOutQueue;

    private Socket socket;

    private PrintWriter printWriter;

    public SinglePortDataOut(Socket socket) throws IOException {
        this.socket = socket;
        initializeSockets();
        dataOutQueue = new ConcurrentLinkedQueue<>();
    }

    private void initializeSockets() throws IOException {
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    public void putInQueue(String data){
        this.dataOutQueue.add(data);
    }

    private String getFromQueue(){
        return dataOutQueue.isEmpty() ? null : dataOutQueue.poll();
    }

    @Override
    public void run() {

        while (true){
            String data = this.getFromQueue();
            if(data != null) {
                printWriter.println(data);
                printWriter.flush();
                System.out.println("Sent " + data + " to LightControllerService");
            }
        }
    }
}
