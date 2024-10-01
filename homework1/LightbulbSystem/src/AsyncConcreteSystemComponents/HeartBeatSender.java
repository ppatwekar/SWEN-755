package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncOutputOnlyComponent;
import PortInAndOut.SinglePortDataOut;

import java.util.Random;

public class HeartBeatSender extends AbstractAsyncOutputOnlyComponent {

    private static int SEND_INTERVAL = 1000;
    Random random;
    private long sendInterval = SEND_INTERVAL;
    boolean heartBeatRandom = false;
    public HeartBeatSender(SinglePortDataOut singlePortDataOut) {

        super(singlePortDataOut);
        random = new Random();
    }

    @Override
    public void run() {
        final String beat = "beat";
        while(true) {
            try {

                //should the beats be sent through another port? Ask team...
                this.singlePortDataOut.putInQueue(beat);

                Thread.sleep(generateRandomHeartbeat());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void setHeartBeatState(String state)
    {
        if(state.compareTo("heartbeat")==0)
        {
            heartBeatRandom = true;
        }
        else
        {
            heartBeatRandom = false;
        }
    }
    public long generateRandomHeartbeat()
    {
        long sendInterval = 0;
        if(heartBeatRandom)
        {
            sendInterval = random.nextInt(1001) + 1500;
            System.out.println(sendInterval);
        }
        else
        {
            sendInterval = SEND_INTERVAL;
        }
        return sendInterval;
    }
}
