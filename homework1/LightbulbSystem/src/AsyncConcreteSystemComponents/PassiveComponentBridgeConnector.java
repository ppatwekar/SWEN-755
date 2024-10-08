package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncBidirectionalComponent;

public class PassiveComponentBridgeConnector extends AbstractAsyncBidirectionalComponent {
    @Override
    protected void processInput(String s) {
        this.singlePortDataOut.putInQueue(s);
    }
}
