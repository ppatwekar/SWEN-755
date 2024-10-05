package PortInAndOut;

import AsyncAbstractSystemComponents.DataOutProducer;

import java.util.HashMap;
import java.util.Map;

public class PortDataOutManager {
    //does this need to be concurrent? I don't think so...? since modification operations done only by 1 thread
    private Map<Integer,SinglePortDataOut> portMap;

    private static PortDataOutManager portDataOutManagerInstance;

    private PortDataOutManager(){
        this.portMap = new HashMap<>();
    }

    public static PortDataOutManager getInstance(){
        if(portDataOutManagerInstance == null){
            portDataOutManagerInstance = new PortDataOutManager();
        }
        return portDataOutManagerInstance;
    }

    public void createNewPortDataOut(int port){
        SinglePortDataOut singlePortDataOut = new SinglePortDataOut(port);
        portMap.put(port,singlePortDataOut);
        new Thread(singlePortDataOut).start();
    }


    public void bindOutPutComponentToPortDataOut(int port, DataOutProducer dataOutProducer){
        dataOutProducer.bindToOutPort(portMap.get(port));
    }

}
