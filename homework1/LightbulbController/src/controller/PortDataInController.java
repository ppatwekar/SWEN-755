package controller;


import AsyncConcreteSystemComponents.HeartBeatReceiver;
import AsyncConcreteSystemComponents.SensorReceiverLightbulbSender;

public class PortDataInController {

    private SensorReceiverLightbulbSender sensorReceiverLightbulbSender;

    private HeartBeatReceiver heartBeatReceiver;

    public PortDataInController(){

    }

    public void processInput(String input){
        if(input.startsWith("Sensor")){
            //do something, send some data back
            System.out.println("PortDataInController: Sending to SensorReceiverLightBulbSender");
            sensorReceiverLightbulbSender.addInputDataToQueue(input);
        }
        else if(input.startsWith("beat")){
            heartBeatReceiver.addInputDataToQueue(input);
        }
    }

    public void setHeartBeatReceiver(HeartBeatReceiver heartBeatReceiver) {
        this.heartBeatReceiver = heartBeatReceiver;
    }

    public void setSensorReceiverLightbulbSender(SensorReceiverLightbulbSender sensorReceiverLightbulbSender) {
        this.sensorReceiverLightbulbSender = sensorReceiverLightbulbSender;
    }
}
