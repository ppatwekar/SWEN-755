package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

public abstract class AbstractAsyncBidirectionalComponent extends AbstractAsyncInputOnlyComponent implements DataOutProducer {
    protected SinglePortDataOut singlePortDataOut;

    public AbstractAsyncBidirectionalComponent() {

    }

    @Override
    public void bindToOutPort(SinglePortDataOut singlePortDataOut) {
        this.singlePortDataOut = singlePortDataOut;
    }
}

