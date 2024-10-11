import AsyncConcreteSystemComponents.LightBulb;
import AsyncConcreteSystemComponents.HeartBeatSender;
import AsyncConcreteSystemComponents.PassiveComponentBridgeConnector;
import AsyncConcreteSystemComponents.Sensor;
import FaultMonitor.FaultMonitorService;
import PortInAndOut.PortDataInManager;
import PortInAndOut.PortDataOutManager;
import SocketFun.SocketManager;
import SocketFun.SocketWrapper;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean isActive = false;
        final int  passivePort = 42976;
        final int lightBulbControllerPort = 42975;
        Socket socket = null;

        PortDataInManager portDataInManager = PortDataInManager.getInstance();
        PortDataOutManager portDataOutManager = PortDataOutManager.getInstance();
        int bulbPort = 0;

        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Designate Light Type: active, inactive");
            String input = scanner.nextLine();
            System.out.println(input);
            if (input.equals("active"))
            {
                isActive = true;
                bulbPort = lightBulbControllerPort;
                break;

            }
            if (input.equals("inactive"))
            {
                bulbPort = passivePort;
                break;
            }
        }

        try
        {
            socket = SocketManager.createSocket(bulbPort);

        }
        catch(IOException e)
        {
            FaultMonitorService.reportFault("socket");
        }

        if(isActive)
        {

            //start backup listener
            SocketManager.createServer(passivePort);


            //backup connections
            PassiveComponentBridgeConnector passiveComponentBridgeConnector = new PassiveComponentBridgeConnector();
            portDataInManager.subscribe(lightBulbControllerPort,passiveComponentBridgeConnector,"all");
            portDataOutManager.bindOutPutComponentToPortDataOut(passivePort,passiveComponentBridgeConnector);
            Thread backupThread = new Thread(passiveComponentBridgeConnector);
            backupThread.start();


            System.out.println("Connected to the LightBulb Controller!");

            LightBulb lightBulb = new LightBulb(isActive);
            portDataInManager.subscribe(lightBulbControllerPort,lightBulb,"LightBulb");
            Thread lightBulbThread = new Thread(lightBulb);
            lightBulbThread.start();

            Sensor sensor = new Sensor(lightBulbControllerPort);
            portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort,sensor);
            Thread sensorThread = new Thread(sensor);
            sensorThread.start();
            System.out.println("Main: Starting AsyncConcreteSystemComponents.Sensor");


            HeartBeatSender heartBeatSender = new HeartBeatSender(lightBulbControllerPort);
            portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort, heartBeatSender);
            Thread heartBeatThread = new Thread(heartBeatSender);
            System.out.println("Main: Starting Heartbeat Thread");
            heartBeatThread.start();



            handleCommandInput(socket, heartBeatSender, sensor);


            //PassiveComponentBridgeConnector passiveComponentBridgeConnector = new PassiveComponentBridgeConnector();
            //portDataOutManager.bindOutPutComponentToPortDataOut(passivePort,passiveComponentBridgeConnector);

        }
        else //inactive
        {

            LightBulb lightBulb = new LightBulb(isActive);
            Thread lightBulbThread = new Thread(lightBulb);
            portDataInManager.subscribe(passivePort,lightBulb,"LightBulb");
            lightBulbThread.start();
            System.out.print("Waiting for fault detection");
            while(!FaultMonitorService.faultDetected())
            {

                System.out.print("");
            }
            try
            {
                System.out.println("Connected to the LightBulb Controller!");
                socket = SocketManager.createSocket(lightBulbControllerPort);
                portDataInManager.subscribe(lightBulbControllerPort,lightBulb,"LightBulb");
                System.out.println(socket);
                FaultMonitorService.clearFault();

            }
            catch(IOException e)
            {
                FaultMonitorService.reportFault("socket");
            }


            Sensor sensor = new Sensor(lightBulbControllerPort);
            portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort,sensor);
            Thread sensorThread = new Thread(sensor);
            sensorThread.start();
            System.out.println("Main: Starting AsyncConcreteSystemComponents.Sensor");


            HeartBeatSender heartBeatSender = new HeartBeatSender(lightBulbControllerPort);
            portDataOutManager.bindOutPutComponentToPortDataOut(lightBulbControllerPort, heartBeatSender);
            Thread heartBeatThread = new Thread(heartBeatSender);
            System.out.println("Main: Starting Heartbeat Thread");
            heartBeatThread.start();

            handleCommandInput(socket, heartBeatSender, sensor);




        }












        /*

        Note: Please create a server using the passive port on the active component first

        int passivePort = 455677;
        PassiveComponentBridgeConnector passiveComponentBridgeConnector = new PassiveComponentBridgeConnector();
        portDataInManager.subscribe(lightBulbControllerPort,passiveComponentBridgeConnector,"all");
        portDataOutManager.bindOutPutComponentToPortDataOut(passivePort,passiveComponentBridgeConnector);

         */
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