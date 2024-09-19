import AsyncConcreteSystemComponents.HeartBeatReceiver;
import AsyncConcreteSystemComponents.SensorReceiverLightbulbSender;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.SinglePortDataIn;
import PortInAndOut.SinglePortDataOut;
import controller.PortDataInController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(42975);
        System.out.println("Main: Waiting to connect to the Lightbulb System...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Main: Connected to the Lightbulb system!");


        FaultMonitorService faultMonitorService = new FaultMonitorService();


        //check if it should be serversocket or client socket.
        SinglePortDataOut singlePortDataOut = new SinglePortDataOut(clientSocket);
        Thread portOutThread = new Thread(singlePortDataOut);

        SensorReceiverLightbulbSender sensorReceiverLightbulbSender = new SensorReceiverLightbulbSender(singlePortDataOut);
        Thread sensorReceiverLightBulbSendorThread = new Thread(sensorReceiverLightbulbSender);

        PortDataInController portDataInController = new PortDataInController();

        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(clientSocket,portDataInController);
        Thread portInThread = new Thread(singlePortDataIn);


        HeartBeatReceiver heartBeatReceiver = new HeartBeatReceiver(faultMonitorService,singlePortDataIn);
        Thread heartBeatThread = new Thread(heartBeatReceiver);


        portDataInController.setHeartBeatReceiver(heartBeatReceiver);
        portDataInController.setSensorReceiverLightbulbSender(sensorReceiverLightbulbSender);

        System.out.println("Main: Starting portout thread");
        portOutThread.start();

        System.out.println("Main: Starting portInThread");
        portInThread.start();

        System.out.println("Main: Starting sensorReceiverLightBulbSendorThread");
        sensorReceiverLightBulbSendorThread.start();

        System.out.println("Main: Starting HeartBeat Thread");
        heartBeatThread.start();

        //Sleeps the main thread for 12secs
        Thread.sleep(15000);
        //Shutsdown the process including heartbeatthread
        System.exit(0);
    }
}