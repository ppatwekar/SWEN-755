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
        // Setup the server socket to accept a connection from the Lightbulb System
        ServerSocket serverSocket = new ServerSocket(42975);
        System.out.println("Main: Waiting to connect to the Lightbulb System...");
        Socket clientSocket = serverSocket.accept(); // Blocking call, waits for client connection
        System.out.println("Main: Connected to the Lightbulb System!");

        // Initialize the FaultMonitorService to handle any faults
        FaultMonitorService faultMonitorService = new FaultMonitorService();

        // Set up the port for sending data out to the Lightbulb System
        SinglePortDataOut singlePortDataOut = new SinglePortDataOut(clientSocket);
        Thread portOutThread = new Thread(singlePortDataOut);

        // Set up the sensor receiver and Lightbulb sender to send data to the Lightbulb system
        SensorReceiverLightbulbSender sensorReceiverLightbulbSender = new SensorReceiverLightbulbSender(singlePortDataOut);
        Thread sensorReceiverLightBulbSendorThread = new Thread(sensorReceiverLightbulbSender);

        // Set up the input controller for receiving data from the Lightbulb System
        PortDataInController portDataInController = new PortDataInController();

        // Set up the port for receiving data in from the Lightbulb System
        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(clientSocket, portDataInController);
        Thread portInThread = new Thread(singlePortDataIn);

        // Set up the HeartBeatReceiver to monitor heartbeats from the Lightbulb System
        HeartBeatReceiver heartBeatReceiver = new HeartBeatReceiver(faultMonitorService, singlePortDataIn);
        Thread heartBeatThread = new Thread(heartBeatReceiver);

        // Connect the PortDataInController with the HeartBeatReceiver and the SensorReceiver
        portDataInController.setHeartBeatReceiver(heartBeatReceiver);
        portDataInController.setSensorReceiverLightbulbSender(sensorReceiverLightbulbSender);

        // Start the threads to handle communication and monitoring
        System.out.println("Main: Starting portOut thread");
        portOutThread.start();

        System.out.println("Main: Starting portInThread");
        portInThread.start();

        System.out.println("Main: Starting sensorReceiverLightbulbSenderThread");
        sensorReceiverLightBulbSendorThread.start();

        System.out.println("Main: Starting HeartBeatReceiver Thread");
        heartBeatThread.start();

        // Sleep the main thread to allow the system to operate for 15 seconds
        Thread.sleep(15000);

        // Shut down the process after 15 seconds (all threads are terminated)
        System.out.println("Main: Shutting down system...");
        System.exit(0);
    }
}