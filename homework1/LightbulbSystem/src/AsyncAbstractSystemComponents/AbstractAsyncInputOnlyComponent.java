package AsyncAbstractSystemComponents;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class AbstractAsyncInputOnlyComponent implements DataInSubscriber{

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

    @Override
    public void update(String data) {
        addInputDataToQueue(data);
    }

    protected abstract void processInput(String s);
}
