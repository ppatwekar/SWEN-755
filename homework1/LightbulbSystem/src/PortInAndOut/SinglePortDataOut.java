package PortInAndOut;

import SocketFun.SocketManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SinglePortDataOut extends AbstractPortData implements Runnable{
    private ConcurrentLinkedQueue<String> dataOutQueue;

    public SinglePortDataOut(int port) {
        super(port);
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
            String data = this.getFromQueue();
            if(data != null) {
                SocketManager.getPrintWriter(port).println(data);
                SocketManager.getPrintWriter(port).flush();
                System.out.println("Sent " + data + " to LightControllerService");
            }
        }
    }
}
