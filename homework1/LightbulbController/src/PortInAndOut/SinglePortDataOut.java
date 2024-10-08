package PortInAndOut;

import SocketFun.SocketWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SinglePortDataOut implements Runnable{
    private ConcurrentLinkedQueue<String> dataOutQueue;

    private SocketWrapper socket;

    public SinglePortDataOut(SocketWrapper socket) throws IOException {
        this.socket = socket;
        dataOutQueue = new ConcurrentLinkedQueue<>();
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
            if(socket.isReadyToUse()) {
                String data = this.getFromQueue();
                if (data != null) {
                    socket.getPrintWriter().println(data);
                    socket.getPrintWriter().flush();
                    System.out.println("Sent " + data + " to LightControllerService");
                }
            }
        }
    }
}
