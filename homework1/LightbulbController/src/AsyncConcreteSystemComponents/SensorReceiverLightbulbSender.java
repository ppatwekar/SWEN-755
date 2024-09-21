package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncBidirectionalComponent;
import PortInAndOut.SinglePortDataOut;

public class SensorReceiverLightbulbSender extends AbstractAsyncBidirectionalComponent {

    private int currentGreaterThan50Count;
    private static final int NUM_COUNTS_TO_SWITCH_ON_LIGHT = 3;

    public SensorReceiverLightbulbSender(SinglePortDataOut singlePortDataOut){
        super(singlePortDataOut);
        currentGreaterThan50Count = 0;
    }
    @Override
    protected void processInput(String s) {
        System.out.println("SensorReceiverLightbulbSender: Processing "+s);
        double probability = Double.parseDouble(s.substring(7));
        if(probability > 0.5)
        {
            currentGreaterThan50Count = currentGreaterThan50Count + 1 == 10 ? 10 : (currentGreaterThan50Count + 1);
        }
        else{
            currentGreaterThan50Count = (currentGreaterThan50Count - 1 == 0 ? 0 : currentGreaterThan50Count - 1);
        }

        System.out.println("SensorReceiverLightbulbSender: currentCount="+currentGreaterThan50Count);

        if(currentGreaterThan50Count > NUM_COUNTS_TO_SWITCH_ON_LIGHT){
            this.singlePortDataOut.putInQueue("LightBulb ON");
        }
        else
        {
            this.singlePortDataOut.putInQueue("LightBulb OFF");
        }
    }
}
