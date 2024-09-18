package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataOut;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractAsyncInputOnlyComponent implements Runnable{
    protected final SinglePortDataOut singlePortDataOut;

    //read and write from multiple threads
    protected final ConcurrentLinkedQueue<String> inputDataQueue;

    protected AbstractAsyncInputOnlyComponent(SinglePortDataOut singlePortDataOut) {
        this.singlePortDataOut = singlePortDataOut;
        inputDataQueue = new ConcurrentLinkedQueue<>();
    }

    public void addInputDataToQueue(String inputData){
        this.inputDataQueue.add(inputData);
    }
    protected void processSingleInputData(){
        if (!inputDataQueue.isEmpty()) {
            processInput(inputDataQueue.poll());
        }
    }

    protected abstract void processInput(String s);
}
