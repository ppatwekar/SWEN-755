import AsyncConcreteSystemComponents.LightBulb;
import AsyncConcreteSystemComponents.HeartBeatSender;
import AsyncConcreteSystemComponents.Sensor;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.PortDataInManager;
import PortInAndOut.PortDataOutManager;
import SocketFun.SocketManager;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        PortDataInManager portDataInManager = PortDataInManager.getInstance();
        PortDataOutManager portDataOutManager = PortDataOutManager.getInstance();

        final int lightBulbControllerPort = 42975;
        Socket socket = null;

        try {
            socket = SocketManager.createSocket(lightBulbControllerPort);
        }
        catch(IOException e)
        {
            FaultMonitorService.reportFault("socket");
        }
        System.out.println("Connected to the LightBulb Controller!");


        Sensor sensor = new Sensor(lightBulbControllerPort);
        portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort,sensor);
        Thread sensorThread = new Thread(sensor);
        sensorThread.start();
        System.out.println("Main: Starting AsyncConcreteSystemComponents.Sensor");

        LightBulb lightBulb = new LightBulb();
        portDataInManager.subscribe(lightBulbControllerPort,lightBulb,"LightBulb");
        Thread lightBulbThread = new Thread(lightBulb);
        lightBulbThread.start();


        HeartBeatSender heartBeatSender = new HeartBeatSender(lightBulbControllerPort);
        portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort, heartBeatSender);
        Thread heartBeatThread = new Thread(heartBeatSender);
        System.out.println("Main: Starting Heartbeat Thread");
        heartBeatThread.start();

        handleCommandInput(socket, heartBeatSender, sensor);
    }

    public static void handleCommandInput(Socket socket, HeartBeatSender heartBeatSender, Sensor sensor) throws IOException, InterruptedException {
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