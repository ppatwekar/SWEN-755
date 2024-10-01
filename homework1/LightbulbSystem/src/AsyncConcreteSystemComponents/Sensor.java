package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncOutputOnlyComponent;
import PortInAndOut.SinglePortDataOut;

import java.util.Random;

public class Sensor extends AbstractAsyncOutputOnlyComponent {

    private static final int SEND_FREQUENCY = 1500;
    Boolean sensorCrashMode = false;
    Random randomSensor;
    String sensorState = "random";
    int flickerCounter = 0;
    public Sensor(SinglePortDataOut singlePortDataOut){
        super(singlePortDataOut);
        randomSensor = new Random();
    }

    @Override
    public void run() {
        while(true){
            try {
                String sensorData = generateSensorData();
                this.singlePortDataOut.putInQueue(sensorData);

                Thread.sleep(SEND_FREQUENCY);

            } catch (Exception e) {
                //try to crash the program using this
                System.out.println(e.getMessage());
                System.exit(-1);
                //throw new RuntimeException(e);
            }
        }
    }

    private String generateSensorData() throws Exception {
        //can generate exception 10% of times
        generatePotentiallyRandomException();

        double possiblityThatSensorDetectsSomeone = 0;
        if (sensorState.compareTo("flicker") == 0)
        {
            if(flickerCounter >= 24)
            {
                flickerCounter = 0;
            }
            if (flickerCounter < 12)
            {
                //on
                possiblityThatSensorDetectsSomeone = randomSensor.nextDouble() + 1;
                System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
            }
            else
            {
                //off
                possiblityThatSensorDetectsSomeone = randomSensor.nextDouble() - 1;
                System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
            }
            flickerCounter++;

        }
        else if (sensorState.compareTo("on") == 0)
        {
            possiblityThatSensorDetectsSomeone = randomSensor.nextDouble() + 1;
            System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
        }
        else if (sensorState.compareTo("off") == 0)
        {
            possiblityThatSensorDetectsSomeone = randomSensor.nextDouble() - 1;
            System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
        }
        else
        {
            //generate random sensor data
            possiblityThatSensorDetectsSomeone = randomSensor.nextDouble();
            System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
        }


       return "Sensor "+possiblityThatSensorDetectsSomeone;
    }

    private void generatePotentiallyRandomException() throws Exception
    {
        if(sensorCrashMode)
        {
            double result = Math.random();
            if (result > 0.60) {
                System.out.println(result);
                throw new Exception("Random Exception Occurred in AsyncConcreteSystemComponents.Sensor System...");
            }
        }
    }
    public void setGenerationData(String input)
    {
        if (input.compareTo("flicker") == 0)
        {
            sensorState = "flicker";
        }
        else if (input.compareTo("on") == 0)
        {
            sensorState = "on";
        }
        else if (input.compareTo("off") == 0)
        {
            sensorState = "off";
        }
        else if (input.compareTo("sensor") == 0)
        {
            sensorCrashMode = true;
        }
        else
        {
            sensorState = "random";
        }
    }
}
