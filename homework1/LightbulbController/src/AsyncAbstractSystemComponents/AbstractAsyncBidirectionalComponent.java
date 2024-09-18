package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

public abstract class AbstractAsyncBidirectionalComponent extends AbstractAsyncInputOnlyComponent implements Runnable {
    protected SinglePortDataOut singlePortDataOut;

    public AbstractAsyncBidirectionalComponent(SinglePortDataOut singlePortDataOut) {
        this.singlePortDataOut = singlePortDataOut;
    }
}

