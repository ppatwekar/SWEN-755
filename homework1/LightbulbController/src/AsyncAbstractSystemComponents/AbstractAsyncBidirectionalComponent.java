package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

public abstract class AbstractAsyncBidirectionalComponent extends AbstractAsyncInputOnlyComponent {
    protected SinglePortDataOut singlePortDataOut;

    public AbstractAsyncBidirectionalComponent(SinglePortDataOut singlePortDataOut) {
        this.singlePortDataOut = singlePortDataOut;
    }
}

