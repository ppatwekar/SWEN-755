package PortInAndOut;

import FaultMonitor.FaultMonitorService;
import SocketFun.SocketWrapper;
import controller.PortDataInController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SinglePortDataIn implements Runnable{

    private SocketWrapper socket;

    private PortDataInController portDataInController;

    FaultMonitorService fm;


    public SinglePortDataIn(SocketWrapper socket, PortDataInController portDataInController, FaultMonitorService faultMonitorService) throws IOException {
        this.socket = socket;
        this.portDataInController = portDataInController;
        fm = faultMonitorService;
    }


    @Override
    public void run() {
        while(true){
            if(socket.isReadyToUse()) {
                try {
                    String dataIn = socket.getBufferedReader().readLine();
                    if (dataIn != null) {
                        portDataInController.processInput(dataIn);
                    }else{
                        throw new IOException("Socket Issue");
                    }
                } catch (IOException e) {
                    socket.setReadyToUse(false);
                    fm.reportFault("socket");
                    //throw new RuntimeException(e);
                }
            }
        }
    }

}
