package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

public abstract class AbstractAsyncOutputOnlyComponent implements Runnable{
    protected SinglePortDataOut singlePortDataOut;

    public AbstractAsyncOutputOnlyComponent(SinglePortDataOut singlePortDataOut){
        this.singlePortDataOut = singlePortDataOut;
    }
}
