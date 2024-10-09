package PortInAndOut;

import FaultMonitor.FaultMonitorService;
import SocketFun.SocketManager;
import controller.PortDataInController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SinglePortDataIn extends AbstractPortData implements Runnable{


    private PortDataInController portDataInController;


    public SinglePortDataIn(int port, PortDataInController portDataInController) {
        super(port);
        this.portDataInController = portDataInController;
    }


    @Override
    public void run() {
        while(true){
            try {
                //System.out.println("Running!!!!!!!!!!!!!");
                if(SocketManager.isReadyToUse(port))
                {
                    String dataIn = SocketManager.getBufferedReader(port).readLine();
                    if (dataIn != null) {
                        System.out.println("SinglePortDataIn: Received " + dataIn);
                        portDataInController.processInput(dataIn);
                    }
                }
            } catch (IOException e)
            {
                FaultMonitorService.reportFault("socket");
                break;
                //SocketManager.unableToConnectToPort(port);
            }
        }
    }

}
