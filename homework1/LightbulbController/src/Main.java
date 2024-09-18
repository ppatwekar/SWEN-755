import AsyncConcreteSystemComponents.HeartBeatReceiver;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.SinglePortDataIn;
import controller.PortDataInController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(42975);
        System.out.println("Waiting to connect to the Lightbulb System...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connected to the Lightbulb system!");


        FaultMonitorService faultMonitorService = new FaultMonitorService();

        PortDataInController portDataInController = new PortDataInController();

        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(clientSocket,portDataInController);

        Thread heartBeatThread = new Thread(new HeartBeatReceiver(faultMonitorService,singlePortDataIn));
        heartBeatThread.start();

        //Sleeps the main thread for 12secs
        Thread.sleep(15000);
        //Shutsdown the process including heartbeatthread
        System.exit(0);
    }
}