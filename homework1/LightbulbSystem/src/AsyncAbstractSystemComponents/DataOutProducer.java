package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

public interface DataOutProducer extends Runnable{
    void bindToOutPort(SinglePortDataOut singlePortDataOut);
}
