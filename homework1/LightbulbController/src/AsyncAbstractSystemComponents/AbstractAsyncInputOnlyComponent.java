package AsyncAbstractSystemComponents;

import PortInAndOut.SinglePortDataIn;
import PortInAndOut.SinglePortDataOut;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractAsyncInputOnlyComponent implements Runnable{

    //read and write from multiple threads
    protected final ConcurrentLinkedQueue<String> inputDataQueue;

    protected AbstractAsyncInputOnlyComponent() {
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

    @Override
    public void run() {
        while(true){
            processSingleInputData();
        }
    }

    protected abstract void processInput(String s);
}
