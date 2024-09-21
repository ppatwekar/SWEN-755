import AsyncAbstractSystemComponents.LightBulb;
import AsyncConcreteSystemComponents.HeartBeatSender;
import AsyncConcreteSystemComponents.Sensor;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.SinglePortDataIn;
import PortInAndOut.SinglePortDataOut;
import controller.PortDataInController;

import javax.swing.plaf.TableHeaderUI;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //LightBulb.LightGUI lg = new LightBulb.LightGUI();
        //lg.toggleLight(true);
        //Thread.sleep(5000);
        //lg.toggleLight(false);

        //while(true){}
        //System.exit(0);
        FaultMonitorService faultMonitorService = new FaultMonitorService();
        Socket socket = null;

        try {
            socket = new Socket("localhost", 42975);
        }
        catch(ConnectException e)
        {
            faultMonitorService.reportFault("socket");
        }
        System.out.println("Connected to the LightBulb Controller!");


        SinglePortDataOut singlePortDataOut = new SinglePortDataOut(socket);
        Thread singlePortDataExchangeThread = new Thread(singlePortDataOut);
        System.out.println("Main: Starting data out thread");
        singlePortDataExchangeThread.start();


        Sensor sensor = new Sensor(singlePortDataOut);
        Thread sensorThread = new Thread(sensor);
        sensorThread.start();
        System.out.println("Main: Starting AsyncConcreteSystemComponents.Sensor");

        LightBulb lightBulb = new LightBulb();
        Thread lightBulbThread = new Thread(lightBulb);

        PortDataInController portDataInController = new PortDataInController();
        portDataInController.setComponent(lightBulb);
        SinglePortDataIn singlePortDataIn = new SinglePortDataIn(socket, portDataInController, faultMonitorService);


        Thread dataInThread = new Thread(singlePortDataIn);
        System.out.println("Main: Starting data in Thread");
        dataInThread.start();

        lightBulbThread.start();

        HeartBeatSender heartBeatSender = new HeartBeatSender(singlePortDataOut);
        //only start after the entire system has been started? or start at the beginning?
        Thread heartBeatThread = new Thread(heartBeatSender);
        System.out.println("Main: Starting Heartbeat Thread");
        heartBeatThread.start();

        //Sleeps the main thread for 10secs
        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Light Sensor Fault and Detection Style (flicker, random, on, off, die, heartbeat, sensor)");
            String input = scanner.nextLine();
            System.out.println(input);
            if(input.equals("die"))
            {
                socket.close();
                Thread.sleep(1000);

            }
            if(input.equals("heartbeat"))
            {
                heartBeatSender.setHeartBeatState(input);
            }
            if(input.equals("sensor"))
            {
                sensor.setGenerationData(input);
            }
            else
            {
                sensor.setGenerationData(input);
            }
        }
    }
}