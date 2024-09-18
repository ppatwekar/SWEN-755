package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncOutputOnlyComponent;
import PortInAndOut.SinglePortDataOut;

public class HeartBeatSender extends AbstractAsyncOutputOnlyComponent {

    private static int SEND_INTERVAL = 1000;

    public HeartBeatSender(SinglePortDataOut singlePortDataOut) {
        super(singlePortDataOut);
    }

    @Override
    public void run() {
        final String beat = "beat";
        while(true) {
            try {

                //should the beats be sent through another port? Ask team...
                this.singlePortDataOut.putInQueue(beat);

                Thread.sleep(SEND_INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
