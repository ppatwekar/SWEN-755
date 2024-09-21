package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncOutputOnlyComponent;
import PortInAndOut.SinglePortDataOut;

public class Sensor extends AbstractAsyncOutputOnlyComponent {

    private static final int SEND_FREQUENCY = 1500;

    public Sensor(SinglePortDataOut singlePortDataOut){
        super(singlePortDataOut);
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

        //generate random sensor data
        double possiblityThatSensorDetectsSomeone = Math.random();
        System.out.println("Sensor: Generated "+possiblityThatSensorDetectsSomeone);
       return "Sensor "+possiblityThatSensorDetectsSomeone;
    }

    private void generatePotentiallyRandomException() throws Exception {
        if (Math.random() > 0.70){
            throw new Exception("Random Exception Occurred in AsyncConcreteSystemComponents.Sensor System...");
        }
    }
}
