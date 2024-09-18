import AsyncConcreteSystemComponents.HeartBeatSender;
import AsyncConcreteSystemComponents.Sensor;
import PortInAndOut.SinglePortDataIn;
import PortInAndOut.SinglePortDataOut;
import controller.PortDataInController;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",42975);
        System.out.println("Connected to the LightBulb Controller!");


        SinglePortDataOut singlePortDataOut = new SinglePortDataOut(socket);
        Thread singlePortDataExchangeThread = new Thread(singlePortDataOut);
        System.out.println("Starting data exchange thread");
        singlePortDataExchangeThread.start();



        Sensor sensor = new Sensor(singlePortDataOut);
        Thread sensorThread = new Thread(sensor);
        sensorThread.start();
        System.out.println("Starting AsyncConcreteSystemComponents.Sensor");

        PortDataInController portDataInController = new PortDataInController();

        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(socket, portDataInController);


        //only start after the entire system has been started? or start at the beginning?
        Thread heartBeatThread = new Thread(new HeartBeatSender(singlePortDataOut));
        System.out.println("Starting Heartbeat Thread");
        heartBeatThread.start();

        //Sleeps the main thread for 10secs
        Thread.sleep(10000);
        //Shutsdown the process including heartbeatthread
        //this is how we should shutdown the system so that it never sends heartbeat after this
        //triggering the other system to find a fault
        System.exit(0);
    }
}