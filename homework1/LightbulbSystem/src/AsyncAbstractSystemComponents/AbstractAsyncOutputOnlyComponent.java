package AsyncAbstractSystemComponents;

import PortInAndOut.PortDataOutManager;
import PortInAndOut.SinglePortDataOut;

public abstract class AbstractAsyncOutputOnlyComponent implements DataOutProducer{
    protected int port;

    protected SinglePortDataOut singlePortDataOut;
    public AbstractAsyncOutputOnlyComponent(int port){
        this.port = port;
    }

    @Override
    public void bindToOutPort(SinglePortDataOut singlePortDataOut) {
        this.singlePortDataOut = singlePortDataOut;
    }
}
